package com.cloudtechies.txnvalidator.db;

import com.cloudtechies.txnvalidator.model.TransactionReport;
import com.cloudtechies.txnvalidator.repos.TransactionReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class TransactionReportRepoImpl {

    @Autowired
    TransactionReportRepository transactionReportRepository;

    public void persistTxns(TransactionReport transactionReport) {
        transactionReportRepository.save(transactionReport);
    }

    public Optional<TransactionReport> getExistingTransactionReportIfAny(UUID payloadId, String trnId){
        return transactionReportRepository.findByPayloadIdAndTrnId(payloadId,trnId);
    }
}
