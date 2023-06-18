package com.cloudtechies.txnvalidator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="txn_report")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TxnReportPK.class)
public class TxnReport {

    @Id
    @Column(name="txn_report_id")
    private UUID txnId;

    @Id
    @Column(name="create_ts")
    private Instant createTs;

    @Id
    @Column(name="update_ts")
    private Instant updateTs;

}
