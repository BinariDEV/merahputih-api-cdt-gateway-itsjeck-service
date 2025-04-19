package agus.ramdan.cdt.gateway.itsjeck.service.transfer;

import agus.ramdan.cdt.base.dto.gateway.TransferServiceCode;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public abstract class TransferMapper {

    @Named("formatAmount")
    public String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "0.00";
        }
        return amount.setScale(2, BigDecimal.ROUND_DOWN).toPlainString(); // 2 desimal, tanpa koma
    }

    @Named("mapToTransferServiceCode")
    public Integer mapToTransferServiceCode(String transferServiceCode) {
        return switch (transferServiceCode){
            case "2" -> TransferServiceCode.SKN.getValue();
            case "3" -> TransferServiceCode.RTGS.getValue();
            case "4" -> TransferServiceCode.SMART_ROUTE.getValue();
            default -> TransferServiceCode.BI_FAST.getValue();
        };
    }

    @Mapping(target = "referenceId", source = "transactionNo")
    @Mapping(target = "payerId", source = "bankPayerId")
    @Mapping(target = "mode", constant = "DESTINATION")
    @Mapping(target = "sender.firstname", source = "destinationAccountFirstname")
    @Mapping(target = "sender.lastname", source = "destinationAccountLastname")
    @Mapping(target = "sender.countryIsoCode", constant = "IDN")
    @Mapping(target = "sender.transferServiceCode", source = "transferType", qualifiedByName = "mapToTransferServiceCode")
    @Mapping(target = "source.amount", source = "amount", qualifiedByName = "formatAmount")
    @Mapping(target = "source.currency", constant = "IDR")
    @Mapping(target = "source.countryIsoCode", source = "destinationCountryCode")
    @Mapping(target = "destination.amount", source = "amount", qualifiedByName = "formatAmount")
    @Mapping(target = "destination.currency", constant = "IDR")
    @Mapping(target = "destination.countryIsoCode", source = "destinationCountryCode")
    @Mapping(target = "beneficiary.firstname", source = "destinationAccountFirstname")
    @Mapping(target = "beneficiary.lastname", source = "destinationAccountLastname")
    @Mapping(target = "beneficiary.countryIsoCode", source = "destinationCountryCode")
    @Mapping(target = "beneficiary.account", source = "destinationAccount")
    @Mapping(target = "compliance.sourceOfFunds", constant = "BUSINESS_INVESTMENT")
    @Mapping(target = "compliance.beneficiaryRelationships", constant = "MYSELF")
    @Mapping(target = "compliance.purposeOfRemittances", constant = "BUSINESS")
    @Mapping(target = "notes", source = "description")
    public abstract TransferRequest mapToTransferRequestDTO(TransferBalanceRequestDTO requestDTO);

    @Mapping(target = "transactionId", source = "data.id")
    @Mapping(target = "transactionNo", source = "data.referenceId")
    @Mapping(target = "transactionFee", source = "data.fee", qualifiedByName = "formatAmount")
    @Mapping(target = "description", source = "data.notes")
    @Mapping(target = "message", source = "data.errorMessage")
    public abstract TransferBalanceResponseDTO mapToTransferBalanceResponseDTO(TransferResponse response);
}
