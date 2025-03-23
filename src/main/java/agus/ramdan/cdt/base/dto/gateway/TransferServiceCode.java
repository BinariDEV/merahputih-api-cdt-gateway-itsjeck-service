package agus.ramdan.cdt.base.dto.gateway;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The type of transfer service for a transaction. Default: 1
 * The transfer_service_code must be an integer and can only take one of the following values:
 * 1. BI-FAST : A real-time payment service that enables quick and seamless domestic fund transfers at a lower cost, making it ideal for time-sensitive and cost-efficient transactions.
 * 2. SKN : A national clearing system used for batch processing of domestic payments, with transfers typically completed within a few hours or by the end of the business day.
 * 3. RTGS : A system for high-value transactions, providing immediate, secure transfers processed individually in real-time.
 * 4. Smart Route : An automated solution that optimally selects between BI-Fast and non-BI Fast routes (via Jack Route), ensuring the fastest and most cost-effective transfer method without manual intervention.
 *
 */
@Getter
@RequiredArgsConstructor
public enum TransferServiceCode {
    BI_FAST(1),
    SKN(2),
    RTGS(3),
    SMART_ROUTE(4);
    private final int value;
}
