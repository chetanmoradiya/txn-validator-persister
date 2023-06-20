package com.cloudtechies.txnvalidator.enums;

import java.util.HashMap;
import java.util.Map;

public enum TransactionFieldsProps {
    TRANSACTION_ID("trnId"),
    CONTRACT_TYPE("contractType"),
    ACTION_TYPE("actionType"),
    UTI("uti"),
    LEVEL("level"),
    REPORTING_COUNTER_PARTY_CODE("repCtrPtyCd"),
    REPORTING_COUNTERPARTY_FINANCIAL_STATUS("repCtrPtyFinSts"),
    REPORTING_COUNTERPARTY_SECTOR("repCtrPtySec"),
    NON_REPORTING_COUNTERPARTY_CODE("nonRepCtrPtyCd"),
    NON_REPORTING_COUNTERPARTY_FINANCIAL_STATUS("nonRepCtrPtyFinSts"),
    NON_REPORTING_COUNTERPARTY_SECTOR("nonRepCtrPtySec"),
    COUNTERPARTY_SIDE("ctrPtySide"),
    EVENT_DATE("eventDate"),
    TRADING_VENUE("tradingVenue"),
    MASTER_AGREEMENT_TYPE("mstrAgreementType"),
    VALUE_DATE("valueDt"),
    GENERAL_COLLATERAL_INDICATOR("genCollInd"),
    TYPE_OF_ASSET("typeOfAsset"),
    SECURITY_IDENTIFIER("secIdentifier"),
    CLASSIFICATION_OF_A_SECURITY("clsOfASec"),
    LOAN_BASE_PRODUCT("loanBaseProduct"),
    LOAN_SUB_PRODUCT("loanSubProduct"),
    LOAN_FURTHER_SUB_PRODUCT("loanFurthrSubProd"),
    LOAN_LEI_OF_THE_ISSUER("loanLeiOfIssuer"),
    LOAN_MATURITY_OF_THE_SECURITY("loanMaturityOfSecurity"),
    LOAN_JURISDICTION_OF_THE_ISSUER("loanJurisOfIssuer");

    private final String originalFieldName;

    TransactionFieldsProps(final String entityFieldName){
        originalFieldName=entityFieldName;
    }

    public String getOriginalFieldName(){
        return originalFieldName;
    }

    private static final Map<String,String> TransactionFieldsMap=new HashMap<>();

    static{
        TransactionFieldsMap.put(TRANSACTION_ID.getOriginalFieldName(),"TRANSACTION ID");
        TransactionFieldsMap.put(CONTRACT_TYPE.getOriginalFieldName(),"CONTRACT TYPE");
        TransactionFieldsMap.put(ACTION_TYPE.getOriginalFieldName(),"ACTION TYPE");
        TransactionFieldsMap.put(UTI.getOriginalFieldName(),"UTI");
        TransactionFieldsMap.put(LEVEL.getOriginalFieldName(),"LEVEL");
        TransactionFieldsMap.put(REPORTING_COUNTER_PARTY_CODE.getOriginalFieldName(),"REPORTING COUNTERPARTY CODE");
        TransactionFieldsMap.put(REPORTING_COUNTERPARTY_FINANCIAL_STATUS.getOriginalFieldName(),"REPORTING COUNTERPARTY FINANCIAL STATUS");
        TransactionFieldsMap.put(REPORTING_COUNTERPARTY_SECTOR.getOriginalFieldName(),"REPORTING COUNTERPARTY SECTOR");
        TransactionFieldsMap.put(NON_REPORTING_COUNTERPARTY_CODE.getOriginalFieldName(),"NON-REPORTING COUNTERPARTY CODE");
        TransactionFieldsMap.put(NON_REPORTING_COUNTERPARTY_FINANCIAL_STATUS.getOriginalFieldName(),"NON-REPORTING COUNTERPARTY FINANCIAL STATUS");
        TransactionFieldsMap.put(NON_REPORTING_COUNTERPARTY_SECTOR.getOriginalFieldName(),"NON-REPORTING COUNTERPARTY SECTOR");
        TransactionFieldsMap.put(COUNTERPARTY_SIDE.getOriginalFieldName(),"COUNTERPARTY SIDE");
        TransactionFieldsMap.put(EVENT_DATE.getOriginalFieldName(),"EVENT DATE");
        TransactionFieldsMap.put(TRADING_VENUE.getOriginalFieldName(),"TRADING VENUE");
        TransactionFieldsMap.put(MASTER_AGREEMENT_TYPE.getOriginalFieldName(),"MASTER AGREEMENT TYPE");
        TransactionFieldsMap.put(VALUE_DATE.getOriginalFieldName(),"VALUE DATE");
        TransactionFieldsMap.put(GENERAL_COLLATERAL_INDICATOR.getOriginalFieldName(),"GENERAL COLLATERAL INDICATOR");
        TransactionFieldsMap.put(TYPE_OF_ASSET.getOriginalFieldName(),"TYPE OF ASSET");
        TransactionFieldsMap.put(SECURITY_IDENTIFIER.getOriginalFieldName(),"SECURITY IDENTIFIER");
        TransactionFieldsMap.put(CLASSIFICATION_OF_A_SECURITY.getOriginalFieldName(),"Classification of a security");
        TransactionFieldsMap.put(LOAN_BASE_PRODUCT.getOriginalFieldName(),"Loan Base product");
        TransactionFieldsMap.put(LOAN_SUB_PRODUCT.getOriginalFieldName(),"Loan Sub product");
        TransactionFieldsMap.put(LOAN_FURTHER_SUB_PRODUCT.getOriginalFieldName(),"Loan Further sub product");
        TransactionFieldsMap.put(LOAN_LEI_OF_THE_ISSUER.getOriginalFieldName(),"Loan LEI of the issuer");
        TransactionFieldsMap.put(LOAN_MATURITY_OF_THE_SECURITY.getOriginalFieldName(),"Loan Maturity of the security");
        TransactionFieldsMap.put(LOAN_JURISDICTION_OF_THE_ISSUER.getOriginalFieldName(),"Loan Jurisdiction of the issuer");
    }

    public static String getTransactionFieldMapping(String entityFieldName){
        return TransactionFieldsMap.get(entityFieldName);
    }
}
