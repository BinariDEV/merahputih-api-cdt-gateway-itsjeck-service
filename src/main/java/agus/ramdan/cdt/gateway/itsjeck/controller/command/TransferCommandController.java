package agus.ramdan.cdt.gateway.itsjeck.controller.command;

import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import agus.ramdan.cdt.gateway.itsjeck.service.transfer.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
//@Service
//@EnableCaching
@RestController
@RequestMapping("/api/cdt/core/gateway/transfer/command")
@RequiredArgsConstructor
public class TransferCommandController {

    private final TransferService service;
    @PostMapping("")
    public ResponseEntity<TransferBalanceResponseDTO> create(
            @RequestBody TransferBalanceRequestDTO dto,
            @RequestHeader HttpHeaders headers) {
        log.info("create transfer");
        return ResponseEntity.ok(service.transferCreate(dto));
    }
}