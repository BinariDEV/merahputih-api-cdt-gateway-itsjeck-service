package agus.ramdan.cdt.gateway.itsjeck.service.transfer;


//import agus.ramdan.cdt.base.dto.gateway.ComplianceInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
public class TransferBeneficiary {
    private String firstname;
    private String lastname;
    @JsonProperty("country_iso_code")
    private String countryIsoCode;
    private String account;
}
