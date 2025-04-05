package agus.ramdan.cdt.gateway.itsjeck.service;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.cdt.base.dto.gateway.BeneficiaryRelationships;
import agus.ramdan.cdt.base.dto.gateway.PurposeOfRemittances;
import agus.ramdan.cdt.base.dto.gateway.SourceOfFunds;
import org.springframework.stereotype.Service;

@Service
public class CoreMappingService {
    /**
     * Beneficiary Relationships
     * Core value   Beneficiary Relationships	Description
     * MYSELF	Myself
     * SPOUSE	Spouse
     * CHILDREN	Children
     * PARENT_GRANPARENT	Parent/Grandparent
     * SIBLING_BROTHER_SISTER	Sibling/brother/sister
     * RELATIVE_UNCLE_AUNTIE_COUSIN	Relative/uncle/auntie/cousin
     * FRIEND	Friend
     * BUSINESS_PARTNER	Business partner
     * CUSTOMER	Customer
     * SUPPLIER	Supplier
     * OTHER	Others not listed
     */
    public BeneficiaryRelationships mapBeneficiaryRelationship(String relationship) {
        if (relationship == null) {
            return BeneficiaryRelationships.OTHER;
        }
        try {
            return BeneficiaryRelationships.valueOf(relationship);
        } catch (IllegalArgumentException e) {
        }

        return BeneficiaryRelationships.OTHER;
    }
    /**
     * Source of Funds
     * Core value   Source of Funds	Description
     * BUSINESS_INVESTMENT	Business Investment
     * PERSONAL_SAVINGS	Personal Savings
     * SALARY_WAGE	Salary/Wage
     * LOAN_LOTTERY_WINNINGS	Loan/Lottery Winnings
     * OTHER	Others not listed
     */
    public SourceOfFunds mapSourceOfFunds(String sourceOfFunds) {
        if (sourceOfFunds != null) {
            throw new BadRequestException("Invalid source of funds ");
        }
        try {
            return SourceOfFunds.valueOf(sourceOfFunds);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid source of funds: " + sourceOfFunds);
        }
    }

    /**
     * Purpose of Remittances
     * Core value   Purpose of Remittances	Description
     * BUSINESS	Business
     * FAMILY_SUPPORT	Familly Support
     * EDUCATION	Education
     * INVESTMENT	Investment
     * SAVINGS	Savings
     * OTHER	Others not listed
     */
    public PurposeOfRemittances mapPurposeOfRemittances(String purposeOfRemittances) {
        if (purposeOfRemittances == null) {
            throw new BadRequestException("Invalid purpose of remittances: ");
        }
        try {
            return PurposeOfRemittances.valueOf(purposeOfRemittances);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid purpose of remittances: " + purposeOfRemittances);
        }
    }


}
