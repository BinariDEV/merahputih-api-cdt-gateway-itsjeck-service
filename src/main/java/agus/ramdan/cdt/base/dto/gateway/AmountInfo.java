package agus.ramdan.cdt.base.dto.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * amount	NO	Decimal	Required if the mode is SOURCE
 * currency	YES	String	Source currency ISO code. Using the standard of ISO 4217!
 * country_iso_code	YES	String	Source country ISO-3 code. Using the standard of ISO 3116 country code
 *
 */
@Getter
@Setter
public class AmountInfo {
    private BigDecimal amount;
    private String currency;
    @JsonProperty("country_iso_code")
    private String countryIsoCode;
    @JsonProperty("transfer_service_code")
    private TransferServiceCode transferServiceCode;
}
