package com.cloudtechies.txnvalidator.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TxnReportPersister {
    public void persistTxns(List<String> messages, List<String> payloadIds) {
    }
}
