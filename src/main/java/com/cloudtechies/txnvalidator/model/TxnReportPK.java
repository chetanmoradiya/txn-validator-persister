package com.cloudtechies.txnvalidator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TxnReportPK implements Serializable {
    private UUID txnId;
    private Instant createTs;
}
