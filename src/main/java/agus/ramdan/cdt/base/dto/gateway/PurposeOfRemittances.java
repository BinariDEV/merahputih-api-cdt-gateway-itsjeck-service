package agus.ramdan.cdt.base.dto.gateway;

/**
 * For Local Transfer:
 * BUSINESS	Transactions made for business purposes.
 * EDUCATION	Transactions made for educational payments, such as for schools or universities.
 * OTHERS	Transactions made for purposes other than business or education.
 *
 * For International Transfer:
 * FAMILY_SUPPORT	Family support
 * TUITION_FEES_STUDYING_EXPENSES	Tuition fees or studying expenses
 * CHARITABLE_DONATIONS	Charitable donations
 * TRAVEL_EXPENSES	Travel expenses
 * MEDICAL_EXPENSES	Medical expenses
 * PAY_FOR_GOODS	Pay for goods
 * PAY_FOR_SERVICES	Pay for services
 * OTHER_BUSINESS_EXPENSES	Other business expenses
 * PROPERTY_RENTAL	Property Rental
 * PROPERTY_PURCHASE	Property Purchase
 * GENERAL_MONTHLY_LIVING_EXPENSES	General monthly living expenses
 * PERSONAL_TRANSFER	Personal transfer
 * OTHER	Other purposes
 * CONSULTANCY_ADVISORY_FEES	Consultancy/Advisory Fees
 *
 */
public enum PurposeOfRemittances {
    BUSINESS,
    EDUCATION,
    OTHERS,
    FAMILY_SUPPORT,
    TUITION_FEES_STUDYING_EXPENSES,
    CHARITABLE_DONATIONS,
    TRAVEL_EXPENSES,
    MEDICAL_EXPENSES,
    PAY_FOR_GOODS,
    PAY_FOR_SERVICES,
    OTHER_BUSINESS_EXPENSES,
    PROPERTY_RENTAL,
    PROPERTY_PURCHASE,
    GENERAL_MONTHLY_LIVING_EXPENSES,
    PERSONAL_TRANSFER,
    OTHER,
    CONSULTANCY_ADVISORY_FEES
}
