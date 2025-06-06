package agus.ramdan.cdt.gateway.itsjeck.service.transfer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
public class TransferSender {
    private String firstname;
    private String lastname;
    @JsonProperty("country_iso_code")
    private String countryIsoCode;
    @JsonProperty("transfer_service_code")
    private Integer transferServiceCode;
}
