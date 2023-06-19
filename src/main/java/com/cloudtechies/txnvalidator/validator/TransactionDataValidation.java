package com.cloudtechies.txnvalidator.validator;

import com.cloudtechies.txnvalidator.config.TransactionValidatorProperties;
import com.cloudtechies.txnvalidator.db.TransactionReportPersister;
import com.cloudtechies.txnvalidator.enums.TransactionStatus;
import com.cloudtechies.txnvalidator.exception.UnrecoverableException;
import com.cloudtechies.txnvalidator.kafka.KafkaOutputAdapter;
import com.cloudtechies.txnvalidator.model.TransactionReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TransactionDataValidation {

    @Autowired
    TransactionReportPersister transactionReportPersister;
    @Autowired
    KafkaOutputAdapter kafkaOutputAdapter;

    @Autowired
    TransactionValidatorProperties transactionValidatorProperties;

    public void validateData(List<String> messages, List<String> payloadIds) {
        ObjectMapper objectMapper=new ObjectMapper();
        for(int i=0;i< messages.size();i++){
            TransactionReport transactionReport;
            try {
                transactionReport=objectMapper.readValue(messages.get(i),TransactionReport.class);
            } catch (JsonProcessingException e) {
                log.error("Got error while deserializing message to TransactionReport {}",e.getMessage());
                throw new UnrecoverableException(e.getMessage());
            }
            transactionReport.setTransactionReportId(UUID.randomUUID());
            transactionReport.setPayloadId(UUID.fromString(payloadIds.get(i)));
            transactionReport.setCreateTs(Instant.now());

            String outputTopicMessage;

            //if validation failed then txn status should be set earlier
            if(transactionReport.getTxnStatus()==null){
                transactionReport.setTxnStatus(TransactionStatus.ACPT);
                outputTopicMessage=getSerializeMessage(transactionReport,objectMapper);
                kafkaOutputAdapter.sendMsgToKafka(outputTopicMessage,transactionValidatorProperties.getKafkaValidTxnDataOutputTopic());
            }else{
                outputTopicMessage=getSerializeMessage(transactionReport,objectMapper);
                kafkaOutputAdapter.sendMsgToKafka(outputTopicMessage,transactionValidatorProperties.getKafkaInValidTxnDataOutputTopic());
            }

            //persist transaction after validation
            transactionReportPersister.persistTxns(transactionReport);
        }
    }

    private String getSerializeMessage(TransactionReport transactionReport, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(transactionReport);
        } catch (JsonProcessingException e) {
            log.error("Got error while serializing  message to TransactionReport{}",e.getMessage());
            throw new UnrecoverableException(e.getMessage());
        }
    }
}
