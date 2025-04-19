package agus.ramdan.cdt.gateway.itsjeck.service.transfer;
import agus.ramdan.cdt.base.dto.gateway.QuotationMode;
import agus.ramdan.cdt.base.dto.gateway.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {

    @JsonProperty("status")
    private String status;
    private Object message;

    @JsonProperty("data")
    private LocalTransferData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LocalTransferData {
        // From Request
        Integer id;

        @JsonProperty("reference_id")
        private String referenceId;

        @JsonProperty("callback_url")
        private String callbackUrl;

        @JsonProperty("balance_id")
        private Integer balanceId;

        @JsonProperty("payer_id")
        @Schema(description = "Index number of destination bank")
        private Integer payerId;

        private QuotationMode mode;
        private Map<String,Object> sender;
        private Map<String,Object> source;
        private Map<String,Object> destination;
        private Map<String,Object> beneficiary;
        private String notes;
        /**
         * "created_at": "2023-03-03T16:10:49.812+07:00",
         *         "updated_at": "2023-03-03T16:10:49.936+07:00",
         *         "user_id": 7,
         *         "state": "created",
         *         "amount": 20000,
         *         "paid_at": null,
         *         "rate": "1.0",
         *         "fee": "3000.0",
         *         "partner_id": 6,
         *         "completion_date": null,
         *         "sent_amount": "20000.0",
         *         "state_id": null,
         *         "error_code": null,
         *         "error_message": null,
         *         "notes": "Allowance for January",
         *         "transaction_type": "C2C",
         *         "balance_id": 7,
         *         "payment_channel": null,
         *         "is_regenerated": false,
         *         "receipt_url": null
         */
        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("user_id")
        private Integer userId;

        @JsonProperty("state")
        private String state;

        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("paid_at")
        private String paidAt;

        @JsonProperty("rate")
        private BigDecimal rate;

        @JsonProperty("fee")
        private BigDecimal fee;

        @JsonProperty("partner_id")
        private Integer partnerId;

        @JsonProperty("completion_date")
        private String completionDate;

        @JsonProperty("sent_amount")
        private BigDecimal sentAmount;

        @JsonProperty("state_id")
        private Integer stateId;

        @JsonProperty("error_code")
        private String errorCode;

        @JsonProperty("error_message")
        private String errorMessage;

        @JsonProperty("transaction_type")
        private TransactionType transactionType;

        @JsonProperty("payment_channel")
        private String paymentChannel;

        @JsonProperty("is_regenerated")
        private Boolean isRegenerated;

        @JsonProperty("receipt_url")
        private String receiptUrl;
    }
}
