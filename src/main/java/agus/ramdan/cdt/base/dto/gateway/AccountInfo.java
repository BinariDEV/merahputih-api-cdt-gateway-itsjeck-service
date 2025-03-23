package agus.ramdan.cdt.base.dto.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * generic account info
 * See {@link AccountCustomerInfo}, {@link AccountBusinessInfo} and {@link AccountLocalInfo}
 */
public class AccountInfo {
    @JsonProperty("account_no")
    private String firstname;
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
