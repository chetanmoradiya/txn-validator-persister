package com.cloudtechies.txnvalidator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TransactionReportPK implements Serializable {

    private UUID transactionReportId;

    private Instant createTs;

}
