package agus.ramdan.cdt.gateway.itsjeck.service.transfer;

import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.Errors;
import agus.ramdan.base.exception.XxxException;
import agus.ramdan.cdt.gateway.itsjeck.config.PaymentGatewayConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class LocalTransferService {
    private final RestClient restClient;
    private final PaymentGatewayConfig paymentGatewayConfig;
    private final ObjectMapper objectMapper;
    protected Errors convertToError(Map<String,Object> message) {
        if (message == null) {
            return new Errors("Error create", "Error create");
        }
        List<ErrorValidation> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : message.entrySet()) {
            if (entry.getValue() instanceof List) {
                List<String> errorList = (List<String>) entry.getValue();
                for (String error : errorList) {
                    ErrorValidation.add(list, error, entry.getKey(), null);
                }
            }
        }
        return new Errors("Error create", list.toArray(new ErrorValidation[0]));
    }
    @Retryable(
            retryFor = {ReTryAbleException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000))
    public TransferResponse create(TransferRequest serviceRequest,Integer transactionId) {
        String url = paymentGatewayConfig.getBaseUrl() + paymentGatewayConfig.getLocalTransfer().getPath();
        String bodyRequest = "";
        try {
            bodyRequest = objectMapper.writeValueAsString(serviceRequest);
        } catch (JsonProcessingException e) {
            log.error("Error create: {}", e.getMessage());
            throw new XxxException("Error transferCreate", 500, null, null, new Errors("Error create", e.getMessage()));
        }
        log.info("Request transferCreate: {}", bodyRequest);
        TransferResponse serviceResponse = restClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyRequest)
                .exchange((request, response) -> {
                    try {
                        return objectMapper.readValue(response.getBody(), TransferResponse.class);
                    } catch (JsonProcessingException e) {
                        log.error("Error create: {}", e.getMessage());
                        throw new XxxException("Error transferCreate", response.getStatusCode().value(), null, null, new Errors("Error create", e.getMessage()));
                    }

                });
        if (serviceResponse == null || serviceResponse.getStatus() == null) {
            log.error("Error create: response body is null");
            throw new ReTryAbleException("Error create : response body is null", 500);
        }

        if ("422".equals(serviceResponse.getStatus()) || "400".equals(serviceResponse.getStatus())) {
            Errors errors;
            if (serviceResponse.getMessage() instanceof Map) {
                Map<String, Object> message = (Map<String, Object>) serviceResponse.getMessage();
                if (message.get("reference_id") instanceof List) {
                    List<String> referenceIdList = (List<String>) message.get("reference_id");
                    if (!referenceIdList.isEmpty()) {
                        if (referenceIdList.contains("has already been taken")) {
                            return this.getTransferByReferenceId(serviceRequest.getReferenceId());
                        }
                    }
                }
                errors = convertToError(message);
            } else if (serviceResponse.getMessage() instanceof String) {
                errors = new Errors("Error create", serviceResponse.getMessage());
            } else {
                errors = new Errors("Error create", serviceResponse.getMessage().toString());
            }
            throw new XxxException("Error transferCreate", Integer.valueOf(serviceResponse.getStatus()), null, null, errors);
        }
        if (!"200".equals(serviceResponse.getStatus())) {
            log.error("Error create: response body is null");
            throw new ReTryAbleException("Error create : response body is null", Integer.parseInt(serviceResponse.getStatus()));
        }
        if (serviceResponse.getData()==null) {
            return getTransferByReferenceId(serviceRequest.getReferenceId());
        }
        return serviceResponse;
    }

    @Retryable(
            retryFor = {ReTryAbleException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000))
    public TransferResponse confirm(TransferRequest serviceRequest,Integer transactionId){
        String url = paymentGatewayConfig.getBaseUrl()+paymentGatewayConfig.getLocalTransfer().getPath()+"/"+transactionId+"/confirm";
        TransferResponse serviceResponse = restClient
                .post()
                .uri(url)
                .exchange((request, response) -> objectMapper.readValue(response.getBody(), TransferResponse.class));
        if (serviceResponse == null) {
            log.error("Error confirm: response body is null");
            throw new ReTryAbleException("Error confirm : response body is null", 500);
        }
        if (serviceResponse.getData()==null) {
            return getTransferByReferenceId(serviceRequest.getReferenceId());
        }
        return serviceResponse;
    }

    @Recover
    public TransferResponse recover(TransferRequest serviceRequest,Integer transactionId){
        return getTransferByReferenceId(serviceRequest.getReferenceId());
    }

    public TransferResponse getTransferByReferenceId(String referenceId) {
        return restClient
                .get()
                .uri(paymentGatewayConfig.getLocalTransfer().getPath()+"/"+referenceId+"/reference_id")
                .exchange((request, response) ->{
                    if (response.getStatusCode().isError()){
                        log.error("Error Get Transfer By Reference ID: {}", response);
                        Errors err = new Errors("Error Get Transfer By Reference ID", response.getBody());
                        throw new XxxException("Error Get Transfer By Reference ID",response.getStatusCode().value(),null,null, err);
                    }
                    return  objectMapper.readValue(response.getBody(), TransferResponse.class);
                });
    }
}
