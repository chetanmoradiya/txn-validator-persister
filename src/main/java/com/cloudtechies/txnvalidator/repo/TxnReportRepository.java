package com.cloudtechies.txnvalidator.repo;

import com.cloudtechies.txnvalidator.model.TxnReport;
import com.cloudtechies.txnvalidator.model.TxnReportPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TxnReportRepository extends JpaRepository<TxnReport, TxnReportPK> {
    Optional<TxnReport> findByTxnReportId(@Param("txnReportId") UUID txnReportId);
}
