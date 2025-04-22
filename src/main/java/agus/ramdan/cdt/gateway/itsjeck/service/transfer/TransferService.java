package agus.ramdan.cdt.gateway.itsjeck.service.transfer;

import agus.ramdan.base.dto.GatewayCallbackDTO;
import agus.ramdan.base.exception.XxxException;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import agus.ramdan.cdt.gateway.itsjeck.config.PaymentGatewayConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransferService {
    private final LocalTransferService localTransferService;
    private final PaymentGatewayConfig paymentGatewayConfig;
    private final TransferMapper localTransferMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public TransferBalanceResponseDTO transferCreate(TransferBalanceRequestDTO requestDTO) throws JsonProcessingException {
        TransferRequest serviceRequest = localTransferMapper.mapToTransferRequestDTO(requestDTO);
        serviceRequest.setBalanceId(paymentGatewayConfig.getLocalTransfer().getBalanceId());
        serviceRequest.setCallbackUrl(paymentGatewayConfig.getCallbackEndpoint()+"/"+serviceRequest.getReferenceId());
        TransferResponse serviceResponse = localTransferService.create(serviceRequest,null);
        if (serviceResponse == null || serviceResponse.getStatus() == null) {
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
        serviceResponse =localTransferService.confirm(serviceRequest,transactionId);
        if (serviceResponse == null || serviceResponse.getStatus() == null) {
            log.error("Error transferCreate: response body is null");
            throw new XxxException("Error transferCreate : response body is null", 500);
        }
        val data = serviceResponse.getData();
        if (data==null) {
            log.error("Error transferCreate: response body data is null");
            throw new XxxException("Error transferCreate : response body data is null", 500);
        }
        responseDTO.setStatus(mapStatus(data.getState()));
        return responseDTO;
    }

    public TransferBalanceResponseDTO getTransferByReferenceId(String referenceId) {
        TransferResponse transferResponse = localTransferService.getTransferByReferenceId(referenceId);
        return localTransferMapper.mapToTransferBalanceResponseDTO(transferResponse);
    }
    public void sendGatewayCallbackDTO(GatewayCallbackDTO event) {
        kafkaTemplate.send("gateway-callback-topic", event);
    }
    public String mapStatus(String str){
        return switch (str) {
            case "canceled","declined" -> "4";
            case "completed" -> "2";
            default -> "1";
        };
    }
    public void callback(String referenceId, Map<String, Object> requestDTO) {
        log.info("callback referenceId: {}", referenceId);
        HashMap<String, Object> map = new HashMap<>(requestDTO);
        String state = "1";
        if (requestDTO.get("state") instanceof String) {
            state = (String) requestDTO.get("state");
        }
        sendGatewayCallbackDTO(GatewayCallbackDTO.builder()
                .gatewayCode("ITSJECK")
                .transactionNo(referenceId)
                .status(mapStatus(state))
                .message(requestDTO.get("error_message") != null ? requestDTO.get("error_message").toString() : "")
                .timestamp(System.currentTimeMillis())
                .data(map)
                .build());
    }
}
