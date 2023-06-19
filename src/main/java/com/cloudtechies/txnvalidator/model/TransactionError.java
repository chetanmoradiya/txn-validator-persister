package com.cloudtechies.txnvalidator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionError {
    String rejectedField;
    String rejectedReason;
}
