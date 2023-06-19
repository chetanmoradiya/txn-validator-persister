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
        TransactionFieldsMap.put(TRANSACTION_ID.getOriginalFieldName(),"Transaction ID");
        TransactionFieldsMap.put(CONTRACT_TYPE.getOriginalFieldName(),"Contract Type");
        TransactionFieldsMap.put(ACTION_TYPE.getOriginalFieldName(),"Action type");
        TransactionFieldsMap.put(UTI.getOriginalFieldName(),"UTI");
        TransactionFieldsMap.put(LEVEL.getOriginalFieldName(),"Level");
        TransactionFieldsMap.put(REPORTING_COUNTER_PARTY_CODE.getOriginalFieldName(),"Reporting Counterparty Code");
        TransactionFieldsMap.put(REPORTING_COUNTERPARTY_FINANCIAL_STATUS.getOriginalFieldName(),"Reporting Counterparty Financial Status");
        TransactionFieldsMap.put(REPORTING_COUNTERPARTY_SECTOR.getOriginalFieldName(),"Reporting Counterparty Sector");
        TransactionFieldsMap.put(NON_REPORTING_COUNTERPARTY_CODE.getOriginalFieldName(),"Non-Reporting Counterparty Code");
        TransactionFieldsMap.put(NON_REPORTING_COUNTERPARTY_FINANCIAL_STATUS.getOriginalFieldName(),"Non-Reporting Counterparty Financial Status");
        TransactionFieldsMap.put(NON_REPORTING_COUNTERPARTY_SECTOR.getOriginalFieldName(),"Non-Reporting Counterparty Sector");
        TransactionFieldsMap.put(COUNTERPARTY_SIDE.getOriginalFieldName(),"Counterparty Side");
        TransactionFieldsMap.put(EVENT_DATE.getOriginalFieldName(),"Event date");
        TransactionFieldsMap.put(TRADING_VENUE.getOriginalFieldName(),"Trading venue");
        TransactionFieldsMap.put(MASTER_AGREEMENT_TYPE.getOriginalFieldName(),"Master agreement type");
        TransactionFieldsMap.put(VALUE_DATE.getOriginalFieldName(),"Value date");
        TransactionFieldsMap.put(GENERAL_COLLATERAL_INDICATOR.getOriginalFieldName(),"General collateral Indicator");
        TransactionFieldsMap.put(TYPE_OF_ASSET.getOriginalFieldName(),"Type of asset");
        TransactionFieldsMap.put(SECURITY_IDENTIFIER.getOriginalFieldName(),"Security identifier");
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
