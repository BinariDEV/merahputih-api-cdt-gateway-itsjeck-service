package agus.ramdan.cdt.base.dto.gateway;

/**
 * Status Class	Message	Description
 * 1	created	    The transaction has been initiated and is currently in a draft state. At this stage, no funds have been deducted, and the transaction is not yet processed. It awaits confirmation before proceeding.
 * 2	confirmed	The transaction has been confirmed, and the specified amount has been deducted from the company balance. The transaction is in progress and officially released to the bank.
 * 3	submitted	The system is waiting for the final status update from the bank
 * 4	canceled	The transaction has been canceled, but this is only possible when the status is still "Created." Once confirmed, a transaction cannot be canceled.
 * 5	completed	The transaction was successfully processed and confirmed by the bank. The funds have been transferred to the recipient, and the process is finalized.
 * 6	declined	The transaction was rejected by the bank due to reasons such as incorrect account details, or restrictions. The system records this status along with an error code explaining the reason.
 */
public enum TransactionStatus {
    CREATED,
    CONFIRMED,
    SUBMITTED,
    CANCELED,
    COMPLETED,
    DECLINED
}
