package com.cloudtechies.txnvalidator.model;

import com.cloudtechies.txnvalidator.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="transaction_report")
@IdClass(TransactionReportPK.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name="json", typeClass = JsonType.class)
public class TransactionReport {

    @Id
    @Column(name="txn_report_id",columnDefinition = "UUID")
    @JsonProperty("TRANSACTION REPORT ID")
    private UUID transactionReportId;

    @Column(name="payload_id",columnDefinition = "UUID")
    @JsonProperty("PAYLOAD ID")
    private UUID payloadId;

    @Column(name="create_ts")
    @Id
    @JsonProperty("CREATE TIMESTAMP")
    private Instant createTs;

    @Column(name="update_ts")
    @JsonProperty("UPDATE TIMESTAMP")
    private Instant updateTs;

    @Enumerated(EnumType.STRING)
    @Column(name="txn_status")
    @JsonProperty("TRANSACTION STATUS")
    private TransactionStatus txnStatus;

    @Type(type="json")
    @Column(name="rjct_reason")
    @JsonProperty("REJECTED REASONS")
    private List<String> rjctReasons;

    @NotBlank(message="TRANSACTION ID should not be null or blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,100}$", message = "TRANSACTION ID should be 1 to 100 alphanumeric")
    @Column(name="trn_id")
    @JsonProperty("TRANSACTION ID")
    private String trnId;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "CONTRACT TYPE should not be greater than 100 alphanumeric")
    @Column(name="cntrct_type")
    @JsonProperty("CONTRACT TYPE")
    private String contractType;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "ACTION TYPE should not be greater than 100 alphanumeric")
    @Column(name="action_type")
    @JsonProperty("ACTION TYPE")
    private String actionType;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "UTI should not be greater than 100 alphanumeric")
    @Column(name="uti")
    @JsonProperty("UTI")
    private String uti;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "LEVEL should not be greater than 100 alphanumeric")
    @Column(name="level")
    @JsonProperty("LEVEL")
    private String level;

    @NotBlank(message="REPORTING COUNTERPARTY CODE should not be null or blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{20}$", message = "REPORTING COUNTERPARTY CODE should be 20 alphanumeric")
    @Column(name="rep_ctrpty_cd")
    @JsonProperty("REPORTING COUNTERPARTY CODE")
    private String repCtrPtyCd;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "REPORTING COUNTERPARTY FINANCIAL STATUS should not be greater than 100 alphanumeric")
    @Column(name="rep_ctrpty_fin_sts")
    @JsonProperty("REPORTING COUNTERPARTY FINANCIAL STATUS")
    private String repCtrPtyFinSts;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "REPORTING COUNTERPARTY SECTOR should not be greater than 100 alphanumeric")
    @Column(name="rep_ctrpty_sec")
    @JsonProperty("REPORTING COUNTERPARTY SECTOR")
    private String repCtrPtySec;

    @NotBlank(message="NON-REPORTING COUNTERPARTY CODE should not be null or blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{20}$", message = "NON-REPORTING COUNTERPARTY CODE should be 20 alphanumeric")
    @Column(name="non_rep_ctrpty_cd")
    @JsonProperty("NON-REPORTING COUNTERPARTY CODE")
    private String nonRepCtrPtyCd;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "NON-REPORTING COUNTERPARTY FINANCIAL STATUS should not be greater than 100 alphanumeric")
    @Column(name="non_rep_ctrpty_fin_sts")
    @JsonProperty("NON-REPORTING COUNTERPARTY FINANCIAL STATUS")
    private String nonRepCtrPtyFinSts;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "NON-REPORTING COUNTERPARTY SECTOR should not be greater than 100 alphanumeric")
    @Column(name="non_rep_ctrpty_sec")
    @JsonProperty("NON-REPORTING COUNTERPARTY SECTOR")
    private String nonRepCtrPtySec;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "COUNTERPARTY SIDE should not be greater than 100 alphanumeric")
    @Column(name="ctrpty_side")
    @JsonProperty("COUNTERPARTY SIDE")
    private String ctrPtySide;

    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "EVENT DATE should be in yyyy-MM-dd format")
    @Column(name="event_date")
    @JsonProperty("EVENT DATE")
    private String eventDate;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "TRADING VENUE should not be greater than 100 alphanumeric")
    @Column(name="trading_venue")
    @JsonProperty("TRADING VENUE")
    private String tradingVenue;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "MASTER AGREEMENT TYPE should not be greater than 100 alphanumeric")
    @Column(name="mstr_agreement_typ")
    @JsonProperty("MASTER AGREEMENT TYPE")
    private String mstrAgreementType;

    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "VALUE DATE should be in yyyy-MM-dd format")
    @Column(name="value_dt")
    @JsonProperty("VALUE DATE")
    private String valueDt;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "GENERAL COLLATERAL INDICATOR should not be greater than 100 alphanumeric")
    @Column(name="gen_coll_ind")
    @JsonProperty("GENERAL COLLATERAL INDICATOR")
    private String genCollInd;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "TYPE OF ASSET should not be greater than 100 alphanumeric")
    @Column(name="typ_of_asset")
    @JsonProperty("TYPE OF ASSET")
    private String typeOfAsset;

    @NotBlank(message="SECURITY IDENTIFIER should not be null or blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{12}$", message = "SECURITY IDENTIFIER should be 12 alphanumeric")
    @Column(name="sec_identifier")
    @JsonProperty("SECURITY IDENTIFIER")
    private String secIdentifier;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "CLASSIFICATION OF A SECURITY should not be greater than 100 alphanumeric")
    @Column(name="class_of_a_sec")
    @JsonProperty("CLASSIFICATION OF A SECURITY")
    private String clsOfASec;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "LOAN BASE PRODUCT should not be greater than 100 alphanumeric")
    @Column(name="loan_base_prod")
    @JsonProperty("LOAN BASE PRODUCT")
    private String loanBaseProduct;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "LOAN SUB PRODUCT should not be greater than 100 alphanumeric")
    @Column(name="loan_sub_prod")
    @JsonProperty("LOAN SUB PRODUCT")
    private String loanSubProduct;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "LOAN FURTHER SUB PRODUCT should not be greater than 100 alphanumeric")
    @Column(name="loan_fur_sub_prod")
    @JsonProperty("LOAN FURTHER SUB PRODUCT")
    private String loanFurthrSubProd;

    @Pattern(regexp = "^[a-zA-Z0-9]{20}$", message = "LOAN LEI OF THE ISSUER should be 20 alphanumeric")
    @Column(name="loan_lei_of_issuer")
    @JsonProperty("LOAN LEI OF THE ISSUER")
    private String loanLeiOfIssuer;

    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "LOAN MATURITY OF THE SECURITY should be in yyyy-MM-dd format")
    @Column(name="loan_maturity_of_secu")
    @JsonProperty("LOAN MATURITY OF THE SECURITY")
    private String loanMaturityOfSecurity;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,100}$", message = "LOAN JURISDICTION OF THE ISSUER should not be greater than 100 alphanumeric")
    @Column(name="loan_juris_of_issuer")
    @JsonProperty("LOAN JURISDICTION OF THE ISSUER")
    private String loanJurisOfIssuer;
}
