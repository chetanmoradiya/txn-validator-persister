package com.cloudtechies.txnvalidator.model;

import com.cloudtechies.txnvalidator.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
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
    @Column(name="txn_errors")
    @JsonProperty("TRANSACTION ERRORS")
    private List<TransactionError> txnErrors;

    @NotBlank(message="Transaction Id should not be null or blank")
    @Size(max=100)
    @Column(name="trn_id")
    @JsonProperty("TRANSACTION ID")
    private String trnId;

    @Size(max=100)
    @Column(name="cntrct_type")
    @JsonProperty("CONTRACT TYPE")
    private String contractType;

    @Size(max=100)
    @Column(name="action_type")
    @JsonProperty("ACTION TYPE")
    private String actionType;

    @Size(max=100)
    @Column(name="uti")
    @JsonProperty("UTI")
    private String uti;

    @Size(max=100)
    @Column(name="level")
    @JsonProperty("LEVEL")
    private String level;

    @NotBlank(message="Reporting Counterparty Code should not be null or blank")
    @Size(min=20, max=20)
    @Column(name="rep_ctrpty_cd")
    @JsonProperty("REPORTING COUNTERPARTY CODE")
    private String repCtrPtyCd;

    @Size(max=100)
    @Column(name="rep_ctrpty_fin_sts")
    @JsonProperty("REPORTING COUNTERPARTY FINANCIAL STATUS")
    private String repCtrPtyFinSts;

    @Size(max=100)
    @Column(name="rep_ctrpty_sec")
    @JsonProperty("REPORTING COUNTERPARTY SECTOR")
    private String repCtrPtySec;

    @NotBlank(message="Non-Reporting Counterparty Code should not be null or blank")
    @Size(min=20, max=20)
    @Column(name="non_rep_ctrpty_cd")
    @JsonProperty("NON-REPORTING COUNTERPARTY CODE")
    private String nonRepCtrPtyCd;

    @Size(max=100)
    @Column(name="non_rep_ctrpty_fin_sts")
    @JsonProperty("NON-REPORTING COUNTERPARTY FINANCIAL STATUS")
    private String nonRepCtrPtyFinSts;

    @Size(max=100)
    @Column(name="non_rep_ctrpty_sec")
    @JsonProperty("NON-REPORTING COUNTERPARTY SECTOR")
    private String nonRepCtrPtySec;

    @Size(max=100)
    @Column(name="ctrpty_side")
    @JsonProperty("COUNTERPARTY SIDE")
    private String ctrPtySide;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    @Column(name="event_date")
    @JsonProperty("EVENT DATE")
    private LocalDate eventDate;

    @Size(max=100)
    @Column(name="trading_venue")
    @JsonProperty("TRADING VENUE")
    private String tradingVenue;

    @Size(max=100)
    @Column(name="mstr_agreement_typ")
    @JsonProperty("MASTER AGREEMENT TYPE")
    private String mstrAgreementType;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    @Column(name="value_dt")
    @JsonProperty("VALUE DATE")
    private LocalDate valueDt;

    @Size(max=100)
    @Column(name="gen_coll_ind")
    @JsonProperty("GENERAL COLLATERAL INDICATOR")
    private String genCollInd;

    @Size(max=100)
    @Column(name="typ_of_asset")
    @JsonProperty("TYPE OF ASSET")
    private String typeOfAsset;

    @NotBlank(message="Security identifier should not be null or blank")
    @Size(min=12, max=12)
    @Column(name="sec_identifier")
    @JsonProperty("SECURITY IDENTIFIER")
    private String secIdentifier;

    @Size(max=100)
    @Column(name="class_of_a_sec")
    @JsonProperty("CLASSIFICATION OF A SECURITY")
    private String clsOfASec;

    @Size(max=100)
    @Column(name="loan_base_prod")
    @JsonProperty("LOAN BASE PRODUCT")
    private String loanBaseProduct;

    @Size(max=100)
    @Column(name="loan_sub_prod")
    @JsonProperty("LOAN SUB PRODUCT")
    private String loanSubProduct;

    @Size(max=100)
    @Column(name="loan_fur_sub_prod")
    @JsonProperty("LOAN FURTHER SUB PRODUCT")
    private String loanFurthrSubProd;

    @Size(min=20, max=20)
    @Column(name="loan_lei_of_issuer")
    @JsonProperty("LOAN LEI OF THE ISSUER")
    private String loanLeiOfIssuer;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    @Column(name="loan_maturity_of_secu")
    @JsonProperty("LOAN MATURITY OF THE SECURITY")
    private LocalDate loanMaturityOfSecurity;

    @Size(max=100)
    @Column(name="loan_juris_of_issuer")
    @JsonProperty("LOAN JURISDICTION OF THE ISSUER")
    private String loanJurisOfIssuer;

}
