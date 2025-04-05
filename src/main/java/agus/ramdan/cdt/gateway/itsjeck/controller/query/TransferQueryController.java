package agus.ramdan.cdt.gateway.itsjeck.controller.query;

import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import agus.ramdan.cdt.gateway.itsjeck.service.transfer.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/cdt/gateway/itsjeck/local-transfer/query")
@RequiredArgsConstructor
public class TransferQueryController {
    private final TransferService service;
    @GetMapping("/{referenceId}")
    public ResponseEntity<TransferBalanceResponseDTO> getByReferenceId(@PathVariable String referenceId) {
        return ResponseEntity.ok(service.getTransferByReferenceId(referenceId));
    }
}