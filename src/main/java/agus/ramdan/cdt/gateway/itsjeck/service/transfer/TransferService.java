package agus.ramdan.cdt.gateway.itsjeck.service.transfer;

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
    //private final TransferBalanceRepository transferBalanceRepository;

    public TransferBalanceResponseDTO transferCreate(TransferBalanceRequestDTO requestDTO) {
        TransferRequest serviceRequest = localTransferMapper.mapToTransferRequestDTO(requestDTO); // transferBalanceMapper.mapToLocalTransferRequest(requestDTO);

        ResponseEntity<TransferResponse> response = restClient
                .post()
                .uri(paymentGatewayConfig.getLocalTransfer().getPath())
                .contentType(MediaType.APPLICATION_JSON)
                .body(serviceRequest).retrieve()
                .toEntity(TransferResponse.class);
        if (response.getStatusCode().isError()){
            log.error("Error transferCreate: {}", response);
            response.getBody();
            throw new RuntimeException("Error transferCreate");
        }
        TransferResponse serviceResponse = response.getBody();
        if (serviceResponse == null) {
            log.error("Error transferCreate: {}", response);
            throw new RuntimeException("Error transferCreate");
        }

        return localTransferMapper.mapToTransferBalanceResponseDTO(response.getBody());
    }
}
