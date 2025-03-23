package agus.ramdan.cdt.gateway.itsjeck.service.transfer;

import agus.ramdan.cdt.base.dto.gateway.ComplianceInfo;
import agus.ramdan.cdt.base.dto.gateway.QuotationMode;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("callback_url")
    private String callbackUrl;

    @JsonProperty("balance_id")
    private Integer balanceId;

    @JsonProperty("payer_id")
    @Schema(description = "Index number of destination bank")
    private String payerId;

    private QuotationMode mode;
    private TransferSender sender;
    private TransferSource source;
    private TransferDestination destination;
    private TransferBeneficiary beneficiary;
    private ComplianceInfo compliance;
    private String notes;

}