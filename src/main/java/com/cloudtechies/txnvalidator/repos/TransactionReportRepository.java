package com.cloudtechies.txnvalidator.repos;

import com.cloudtechies.txnvalidator.model.TransactionReport;
import com.cloudtechies.txnvalidator.model.TransactionReportPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionReportRepository extends JpaRepository<TransactionReport, TransactionReportPK> {
    Optional<TransactionReport> findByTxnReportId(@Param("transactionReportId") UUID transactionReportId);
}
