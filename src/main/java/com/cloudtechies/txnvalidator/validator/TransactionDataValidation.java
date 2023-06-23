package com.cloudtechies.txnvalidator.validator;

import com.cloudtechies.txnvalidator.config.TransactionValidatorProperties;
import com.cloudtechies.txnvalidator.db.TransactionReportPersister;
import com.cloudtechies.txnvalidator.enums.TransactionStatus;
import com.cloudtechies.txnvalidator.exception.UnrecoverableException;
import com.cloudtechies.txnvalidator.kafka.KafkaOutputAdapter;
import com.cloudtechies.txnvalidator.model.TransactionReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        objectMapper.registerModule(new JavaTimeModule());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

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

            //if rejected reasons are null then it is valid transaction, otherwise invalid transaction
            List<String> rejectedReasons=getRejectedReasons(validator,transactionReport);
            if(rejectedReasons==null){
                transactionReport.setTxnStatus(TransactionStatus.PREENRICHVALID);
                //persist transaction after validation
                transactionReportPersister.persistTxns(transactionReport);
                outputTopicMessage=getSerializeMessage(transactionReport,objectMapper);
                kafkaOutputAdapter.sendMsgToKafka(outputTopicMessage,transactionValidatorProperties.getKafkaPrEnrValidTxnDataOutputTopic());
            }else{
                transactionReport.setTxnStatus(TransactionStatus.RJCT);
                transactionReport.setRjctReasons(rejectedReasons);
                //persist transaction after validation
                transactionReportPersister.persistTxns(transactionReport);
                outputTopicMessage=getSerializeMessage(transactionReport,objectMapper);
                kafkaOutputAdapter.sendMsgToKafka(outputTopicMessage,transactionValidatorProperties.getKafkaRjctTxnDataOutputTopic());
            }
        }
    }

    private List<String> getRejectedReasons(Validator validator, TransactionReport transactionReport) {

        Set<ConstraintViolation<TransactionReport>> violations = validator.validate(transactionReport);
        if (!violations.isEmpty()) {
            List<String> rejectedReasons=new ArrayList<>();
            for(ConstraintViolation<TransactionReport> violation:violations){
                rejectedReasons.add(violation.getMessage());
            }
            return rejectedReasons;
        }
        return null;
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
