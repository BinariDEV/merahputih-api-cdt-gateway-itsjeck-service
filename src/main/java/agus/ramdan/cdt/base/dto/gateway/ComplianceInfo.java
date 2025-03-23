package agus.ramdan.cdt.base.dto.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplianceInfo {
    @JsonProperty("source_of_funds")
    SourceOfFunds sourceOfFunds;
    @JsonProperty("beneficiary_relationships")
    BeneficiaryRelationships beneficiaryRelationships;
    @JsonProperty("purpose_of_remittances")
    PurposeOfRemittances purposeOfRemittances;
}
