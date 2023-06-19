package com.cloudtechies.txnvalidator.repos;

import com.cloudtechies.txnvalidator.model.TransactionReport;
import com.cloudtechies.txnvalidator.model.TransactionReportPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionReportRepository extends JpaRepository<TransactionReport, TransactionReportPK> {
}
