package com.cloudtechies.txnvalidator.model;

import com.cloudtechies.txnvalidator.enums.TransactionStatus;
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
    private UUID transactionReportId;

    @Column(name="payload_id",columnDefinition = "UUID")
    private UUID payloadId;

    @Column(name="create_ts")
    @Id
    private Instant createTs;

    @Column(name="update_ts")
    private Instant updateTs;

    @Enumerated(EnumType.STRING)
    @Column(name="txn_status")
    private TransactionStatus txnStatus;

    @Type(type="json")
    @Column(name="txn_errors")
    private List<TransactionError> txnErrors;

    @NotBlank(message="Transaction Id should not be null or blank")
    @Size(max=100)
    @Column(name="trn_id")
    private String trnId;

    @Size(max=100)
    @Column(name="cntrct_type")
    private String contractType;

    @Size(max=100)
    @Column(name="action_type")
    private String actionType;

    @Size(max=100)
    @Column(name="uti")
    private String uti;

    @Size(max=100)
    @Column(name="level")
    private String level;

    @NotBlank(message="Reporting Counterparty Code should not be null or blank")
    @Size(min=20, max=20)
    @Column(name="rep_ctrpty_cd")
    private String repCtrPtyCd;

    @Size(max=100)
    @Column(name="rep_ctrpty_fin_sts")
    private String repCtrPtyFinSts;

    @Size(max=100)
    @Column(name="rep_ctrpty_sec")
    private String repCtrPtySec;

    @NotBlank(message="Non-Reporting Counterparty Code should not be null or blank")
    @Size(min=20, max=20)
    @Column(name="non_rep_ctrpty_cd")
    private String nonRepCtrPtyCd;

    @Size(max=100)
    @Column(name="non_rep_ctrpty_fin_sts")
    private String nonRepCtrPtyFinSts;

    @Size(max=100)
    @Column(name="non_rep_ctrpty_sec")
    private String nonRepCtrPtySec;

    @Size(max=100)
    @Column(name="ctrpty_side")
    private String ctrPtySide;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    @Column(name="event_date")
    private LocalDate eventDate;

    @Size(max=100)
    @Column(name="trading_venue")
    private String tradingVenue;

    @Size(max=100)
    @Column(name="mstr_agreement_typ")
    private String mstrAgreementType;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    @Column(name="value_dt")
    private LocalDate valueDt;

    @Size(max=100)
    @Column(name="gen_coll_ind")
    private String genCollInd;

    @Size(max=100)
    @Column(name="typ_of_asset")
    private String typeOfAsset;

    @NotBlank(message="Security identifier should not be null or blank")
    @Size(min=12, max=12)
    @Column(name="sec_identifier")
    private String secIdentifier;

    @Size(max=100)
    @Column(name="class_of_a_sec")
    private String clsOfASec;

    @Size(max=100)
    @Column(name="loan_base_prod")
    private String loanBaseProduct;

    @Size(max=100)
    @Column(name="loan_sub_prod")
    private String loanSubProduct;

    @Size(max=100)
    @Column(name="loan_fur_sub_prod")
    private String loanFurthrSubProd;

    @Size(min=20, max=20)
    @Column(name="loan_lei_of_issuer")
    private String loanLeiOfIssuer;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    @Column(name="loan_maturity_of_secu")
    private LocalDate loanMaturityOfSecurity;

    @Size(max=100)
    @Column(name="loan_juris_of_issuer")
    private String loanJurisOfIssuer;

}
