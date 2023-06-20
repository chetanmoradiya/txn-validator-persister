package com.cloudtechies.txnvalidator.validator;

import com.cloudtechies.txnvalidator.config.TransactionValidatorProperties;
import com.cloudtechies.txnvalidator.db.TransactionReportPersister;
import com.cloudtechies.txnvalidator.kafka.KafkaOutputAdapter;
import com.cloudtechies.txnvalidator.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionDataValidationTest {

    @InjectMocks
    TransactionDataValidation transactionDataValidation;
    @Mock
    TransactionReportPersister transactionReportPersister;
    @Mock
    KafkaOutputAdapter kafkaOutputAdapter;
    @Mock
    TransactionValidatorProperties transactionValidatorProperties;

    @Test
    void testValidation(){
        List<String> messages=List.of(TestUtil.getMessage1());
        List<String> payloadId=List.of("c6a8669e-ee95-4c42-9ef6-4a9b61380164");
        transactionDataValidation.validateData(messages,payloadId);
    }

}