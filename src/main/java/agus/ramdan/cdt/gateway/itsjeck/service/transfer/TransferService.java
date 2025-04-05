package agus.ramdan.cdt.gateway.itsjeck.service.transfer;

import agus.ramdan.base.exception.Errors;
import agus.ramdan.base.exception.XxxException;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import agus.ramdan.cdt.gateway.itsjeck.config.PaymentGatewayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransferService {
    private final RestClient restClient;
    private final PaymentGatewayConfig paymentGatewayConfig;
    private final TransferMapper localTransferMapper;
    public TransferBalanceResponseDTO transferCreate(TransferBalanceRequestDTO requestDTO) {
        TransferRequest serviceRequest = localTransferMapper.mapToTransferRequestDTO(requestDTO);
        ResponseEntity<TransferResponse> response = restClient
                .post()
                .uri(paymentGatewayConfig.getLocalTransfer().getPath())
                .contentType(MediaType.APPLICATION_JSON)
                .body(serviceRequest).retrieve()
                .toEntity(TransferResponse.class);
        if (response.getStatusCode().isError()){
            log.error("Error transferCreate: {}", response);
            Errors err = new Errors("Error transferCreate", response.getBody());
            throw new XxxException("Error transferCreate",response.getStatusCode().value(),null,null, err);
        }
        TransferResponse serviceResponse = response.getBody();
        if (serviceResponse == null) {
            log.error("Error transferCreate: {}", response);
            throw new XxxException("Error transferCreate : response body is null", 500);
        }
        Integer transactionId = serviceResponse.getData().getId();
        response = restClient
                .post()
                .uri(paymentGatewayConfig.getLocalTransfer().getPath()+"/"+transactionId+"/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .body(serviceRequest).retrieve()
                .toEntity(TransferResponse.class);
        if (response.getStatusCode().isError()){
            log.error("Error transfer Confirm: {}", response);
            Errors err = new Errors("Error Transfer Confirm", response.getBody());
            throw new XxxException("Error transferCreate",response.getStatusCode().value(),null,null, err);
        }
        return localTransferMapper.mapToTransferBalanceResponseDTO(serviceResponse);
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
}
