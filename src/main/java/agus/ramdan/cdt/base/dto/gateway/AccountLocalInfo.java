package agus.ramdan.cdt.base.dto.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * AccountBusinessInfo
 *  "sender": {
 *             "firstname": "John Doe",
 *             "country_iso_code": "IDN"
 *         },
 *  "beneficiary": {
 *             "firstname": "BRI Simulator A",
 *             "country_iso_code": "IDN",
 *             "account": "111112222233333",
 *             "bank": "BRI"
 *         },
 *       Info account business customer
 *       "account_no": "1234567890",
 *         "account_name": "John Doe",
 *         "bank_code": "014",
 *         "region_code": "01",
 *         "country_code": "IDN",
 *         "customer_status": "01",
 *         "customer_type": "01",
 *         "account_type": "SVGS",
 */
@Getter
@Setter
public class AccountLocalInfo {
    @JsonProperty("account_no")
    private String accountNo;
    @Schema(description = "Account Bank Holder Name")
    @JsonProperty("account_name")
    private String accountName;
    @JsonProperty("account_type")
    private AccountType accountType;
    private String currency;
    @JsonProperty("bank_code")
    private String bankCode;
    @JsonProperty("region_code")
    private String regionCode;
    @Schema(description = "Country code ISO 3166-1 alpha-3")
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("customer_status")
    private String customerStatus;
    @JsonProperty("customer_type")
    private String customerType;
}
