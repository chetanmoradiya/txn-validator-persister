package com.cloudtechies.txnvalidator.db;

import com.cloudtechies.txnvalidator.model.TransactionReport;
import com.cloudtechies.txnvalidator.repos.TransactionReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionReportPersister {

    @Autowired
    TransactionReportRepository transactionReportRepository;

    public void persistTxns(TransactionReport transactionReport) {
        transactionReportRepository.save(transactionReport);
    }
}
