package agus.ramdan.cdt.gateway.itsjeck.controller.command;

import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import agus.ramdan.cdt.gateway.itsjeck.service.transfer.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
//@Service
//@EnableCaching
@RestController
@RequestMapping("/callback")
@RequiredArgsConstructor
public class CallbackCommandController {
    private final TransferService service;
    @PostMapping("/{referenceId}")
    public ResponseEntity<TransferBalanceResponseDTO> create(
            @PathVariable String referenceId,
            @RequestBody Map<String,Object> dto,
            @RequestHeader HttpHeaders headers) {
        log.info("referenceId: {}", referenceId);
        service.callback(referenceId, dto);
        return ResponseEntity.accepted().build();
    }
}