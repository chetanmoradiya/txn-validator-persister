package com.cloudtechies.txnvalidator.validator;

import com.cloudtechies.txnvalidator.config.TransactionValidatorProperties;
import com.cloudtechies.txnvalidator.db.TransactionReportRepoImpl;
import com.cloudtechies.txnvalidator.enums.TransactionStatus;
import com.cloudtechies.txnvalidator.kafka.KafkaOutputAdapter;
import com.cloudtechies.txnvalidator.model.TransactionReport;
import com.cloudtechies.txnvalidator.utils.TestUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TransactionDataValidationTest {

    @InjectMocks
    TransactionDataValidation transactionDataValidation;
    @Mock
    TransactionReportRepoImpl transactionReportPersister;
    @Mock
    KafkaOutputAdapter kafkaOutputAdapter;
    @Mock
    TransactionValidatorProperties transactionValidatorProperties;
    @Captor
    ArgumentCaptor<TransactionReport> transactionReportCaptor;

    List<String> payloadId;

    @BeforeEach
    void setup()
    {
        payloadId=Arrays.asList("c6a8669e-ee95-4c42-9ef6-4a9b61380164");

        when(transactionValidatorProperties.getKafkaRjctTxnDataOutputTopic()).thenReturn(null);
        doNothing().when(transactionReportPersister).persistTxns(any());
        doNothing().when(kafkaOutputAdapter).sendMsgToKafka(any(),any());
    }


    @Test
    void testValidation_test_Valid_Json(){
        List<String> messages = Arrays.asList(TestUtil.getMessage1());

        transactionDataValidation.validateData(messages,payloadId);

        verify(transactionReportPersister,times(1)).persistTxns(transactionReportCaptor.capture());
        TransactionReport transactionReport=transactionReportCaptor.getValue();
        assertNull(transactionReport.getRjctReasons());
        assertNotNull(transactionReport.getTransactionReportId());
        assertEquals(UUID.fromString("c6a8669e-ee95-4c42-9ef6-4a9b61380164"),transactionReport.getPayloadId());
        assertEquals(TransactionStatus.PREENRICHVALID,transactionReport.getTxnStatus());
    }

    @Test
    void testValidation_test_Mandatory_fields_Validation() throws JSONException
    {
        JSONObject jsonObject=new JSONObject(TestUtil.getMessage1());
        jsonObject.put("TRANSACTION ID", JSONObject.NULL);
        jsonObject.put("REPORTING COUNTERPARTY CODE", JSONObject.NULL);
        jsonObject.put("NON-REPORTING COUNTERPARTY CODE", JSONObject.NULL);
        jsonObject.put("SECURITY IDENTIFIER", JSONObject.NULL);
        String msg=jsonObject.toString();
        List<String> messages = Arrays.asList(msg);

        transactionDataValidation.validateData(messages,payloadId);

        verify(transactionReportPersister,times(1)).persistTxns(transactionReportCaptor.capture());
        TransactionReport transactionReport=transactionReportCaptor.getValue();
        assertNotNull(transactionReport.getRjctReasons());
        assertEquals(4,transactionReport.getRjctReasons().size());
        assertTrue(transactionReport.getRjctReasons().get(0).contains("should not be null or blank"));
        assertTrue(transactionReport.getRjctReasons().get(1).contains("should not be null or blank"));
        assertTrue(transactionReport.getRjctReasons().get(2).contains("should not be null or blank"));
        assertTrue(transactionReport.getRjctReasons().get(3).contains("should not be null or blank"));
        assertEquals(TransactionStatus.RJCT,transactionReport.getTxnStatus());
        verify(kafkaOutputAdapter,times(1)).sendMsgToKafka(any(),any());
    }

    @Test
    void testValidation_test_ISIN_Invalid() throws JSONException
    {
        JSONObject jsonObject=new JSONObject(TestUtil.getMessage1());
        jsonObject.put("SECURITY IDENTIFIER", "123");
        String msg=jsonObject.toString();
        List<String> messages = Arrays.asList(msg);

        transactionDataValidation.validateData(messages,payloadId);

        verify(transactionReportPersister,times(1)).persistTxns(transactionReportCaptor.capture());
        TransactionReport transactionReport=transactionReportCaptor.getValue();
        assertNotNull(transactionReport.getRjctReasons());
        assertEquals(1,transactionReport.getRjctReasons().size());
        assertEquals("SECURITY IDENTIFIER should be 12 alphanumeric",transactionReport.getRjctReasons().get(0));
        assertEquals(TransactionStatus.RJCT,transactionReport.getTxnStatus());
        verify(kafkaOutputAdapter,times(1)).sendMsgToKafka(any(),any());
    }

    @Test
    void testValidation_test_LEI_fields_Invalid() throws JSONException
    {

        JSONObject jsonObject=new JSONObject(TestUtil.getMessage1());
        jsonObject.put("REPORTING COUNTERPARTY CODE", "abc def");
        jsonObject.put("NON-REPORTING COUNTERPARTY CODE", "dda_a-da$d");
        jsonObject.put("LOAN LEI OF THE ISSUER", "ddadadadadadadadaffifajdfaajdajfajfjafafafdad");
        String msg=jsonObject.toString();
        List<String> messages = Arrays.asList(msg);

        transactionDataValidation.validateData(messages,payloadId);

        verify(transactionReportPersister,times(1)).persistTxns(transactionReportCaptor.capture());
        TransactionReport transactionReport=transactionReportCaptor.getValue();
        assertNotNull(transactionReport.getRjctReasons());
        assertEquals(3,transactionReport.getRjctReasons().size());
        assertTrue(transactionReport.getRjctReasons().get(0).contains("should be 20 alphanumeric"));
        assertTrue(transactionReport.getRjctReasons().get(1).contains("should be 20 alphanumeric"));
        assertTrue(transactionReport.getRjctReasons().get(2).contains("should be 20 alphanumeric"));
        assertEquals(TransactionStatus.RJCT,transactionReport.getTxnStatus());
        verify(kafkaOutputAdapter,times(1)).sendMsgToKafka(any(),any());
    }

    @Test
    void testValidation_test_Date_Invalid() throws JSONException
    {
        JSONObject jsonObject=new JSONObject(TestUtil.getMessage1());
        jsonObject.put("EVENT DATE", "22-06-2023");
        jsonObject.put("VALUE DATE", "22/06/2023");
        jsonObject.put("LOAN MATURITY OF THE SECURITY", "2023-13-02");
        String msg=jsonObject.toString();
        List<String> messages = Arrays.asList(msg);

        transactionDataValidation.validateData(messages,payloadId);

        verify(transactionReportPersister,times(1)).persistTxns(transactionReportCaptor.capture());
        TransactionReport transactionReport=transactionReportCaptor.getValue();
        assertNotNull(transactionReport.getRjctReasons());
        assertEquals(3,transactionReport.getRjctReasons().size());
        assertTrue(transactionReport.getRjctReasons().get(0).contains("should be in yyyy-MM-dd format"));
        assertTrue(transactionReport.getRjctReasons().get(1).contains("should be in yyyy-MM-dd format"));
        assertTrue(transactionReport.getRjctReasons().get(2).contains("should be in yyyy-MM-dd format"));
        assertEquals(TransactionStatus.RJCT,transactionReport.getTxnStatus());
        verify(kafkaOutputAdapter,times(1)).sendMsgToKafka(any(),any());
    }

    @Test
    void testValidation_test_otherFields_greaterThan_100_alphanumeric() throws JSONException
    {

        JSONObject jsonObject=new JSONObject(TestUtil.getMessage1());
        jsonObject.put("MASTER AGREEMENT TYPE", TestUtil.getMessage1());
        jsonObject.put("COUNTERPARTY SIDE", TestUtil.getMessage1());
        String msg=jsonObject.toString();
        List<String> messages = Arrays.asList(msg);

        transactionDataValidation.validateData(messages,payloadId);

        verify(transactionReportPersister,times(1)).persistTxns(transactionReportCaptor.capture());
        TransactionReport transactionReport=transactionReportCaptor.getValue();
        assertNotNull(transactionReport.getRjctReasons());
        assertEquals(2,transactionReport.getRjctReasons().size());
        assertTrue(transactionReport.getRjctReasons().get(0).contains("should not be greater than 100 alphanumeric"));
        assertTrue(transactionReport.getRjctReasons().get(1).contains("should not be greater than 100 alphanumeric"));
        assertEquals(TransactionStatus.RJCT,transactionReport.getTxnStatus());
        verify(kafkaOutputAdapter,times(1)).sendMsgToKafka(any(),any());
    }

}