package com.cloudtechies.txnvalidator.repos;

import com.cloudtechies.txnvalidator.model.TransactionReport;
import com.cloudtechies.txnvalidator.model.TransactionReportPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TransactionReportRepository extends JpaRepository<TransactionReport, TransactionReportPK> {

    Optional<TransactionReport> findByPayloadIdAndTrnId(@Param("payloadId") UUID payloadId, @Param("trnId") String trnId);
}
