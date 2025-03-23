package agus.ramdan.cdt.gateway.itsjeck.service.transfer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * source[amount]	NO	Decimal	Required if the mode is SOURCE
 * source[currency]	YES	String	Source currency ISO code. Using the standard of ISO 4217
 * source[country_iso_code]	YES	String	Source country ISO-3 code. Using the standard of ISO 3116 country code
 */
@Getter
@Setter
public class TransferDestination {
    private BigDecimal amount;
    private String currency;
    @JsonProperty("country_iso_code")
    private String countryIsoCode;
}
