package agus.ramdan.cdt.gateway.itsjeck.service.transfer;

import agus.ramdan.base.exception.Errors;
import agus.ramdan.base.exception.XxxException;
import agus.ramdan.cdt.base.dto.GatewayCallbackDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import agus.ramdan.cdt.gateway.itsjeck.config.PaymentGatewayConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransferService {
    private final RestClient restClient;
    private final PaymentGatewayConfig paymentGatewayConfig;
    private final TransferMapper localTransferMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransferBalanceResponseDTO transferCreate(TransferBalanceRequestDTO requestDTO) throws JsonProcessingException {
        TransferRequest serviceRequest = localTransferMapper.mapToTransferRequestDTO(requestDTO);
        String url = paymentGatewayConfig.getBaseUrl()+paymentGatewayConfig.getLocalTransfer().getPath();
        serviceRequest.setCallbackUrl(paymentGatewayConfig.getCallbackEndpoint()+"/"+serviceRequest.getReferenceId());
        String bodyRequest = objectMapper.writeValueAsString(serviceRequest);
        log.info("Request transferCreate: {}", bodyRequest);
        val responseSpec = restClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyRequest).retrieve();
        TransferResponse serviceResponse = responseSpec
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    Errors err = new Errors("Error 4xx transferCreate", res.getBody());
                    throw new XxxException("Error transferCreate",res.getStatusCode().value(),null,null, err);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    Errors err = new Errors("Error 5xx transferCreate", res.getBody());
                    throw new XxxException("Error transferCreate",res.getStatusCode().value(),null,null, err);
                })
                .body(TransferResponse.class);
        if (serviceResponse == null) {
            log.error("Error transferCreate: response body is null");
            throw new XxxException("Error transferCreate : response body is null", 500);
        }
        val responseDTO = localTransferMapper.mapToTransferBalanceResponseDTO(serviceResponse);
        /**
         * List of status transaction:
         * 1 = On Process
         * 2 = Success
         * 4 = Failed
         * 5 = Reverse
         */
        responseDTO.setStatus("1");
        Integer transactionId = serviceResponse.getData().getId();
        try {
            Map response = restClient
                    .post()
                    .uri(url+"/"+transactionId+"/confirm")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(serviceRequest)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        Errors err = new Errors("Error 4xx confirm", res.getBody());
                        throw new XxxException("Error confirm",res.getStatusCode().value(),null,null, err);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        Errors err = new Errors("Error 5xx confirm", res.getBody());
                        throw new XxxException("Error confirm",res.getStatusCode().value(),null,null, err);
                    })
                    .body(Map.class);
            responseDTO.setStatus("2");
        } catch (XxxException e) {

        }
        return responseDTO;
    }

    public TransferBalanceResponseDTO getTransferByReferenceId(String referenceId) {
        ResponseEntity<TransferResponse> response = restClient
                .post()
                .uri(paymentGatewayConfig.getLocalTransfer().getPath()+"/"+referenceId+"/reference_id")
                .retrieve()
                .toEntity(TransferResponse.class);
        if (response.getStatusCode().isError()){
            log.error("Error Get Transfer By Reference ID: {}", response);
            Errors err = new Errors("Error Get Transfer By Reference ID", response.getBody());
            throw new XxxException("Error Get Transfer By Reference ID",response.getStatusCode().value(),null,null, err);
        }
        return localTransferMapper.mapToTransferBalanceResponseDTO(response.getBody());
    }

    public void sendGatewayCallbackDTO(GatewayCallbackDTO event) {
        kafkaTemplate.send("gateway-callback-topic", event);
    }

    public void callback(String referenceId, Map<String, Object> requestDTO) {
        log.info("callback referenceId: {}", referenceId);
        HashMap<String, Object> map = new HashMap<>(requestDTO);
        map.put("transaction_no", referenceId);
        map.put("status", "1");
        sendGatewayCallbackDTO(GatewayCallbackDTO.builder()
                .gatewayCode("ITSJECK")
                .timestamp(System.currentTimeMillis())
                .data(map)
                .build());
    }
}
