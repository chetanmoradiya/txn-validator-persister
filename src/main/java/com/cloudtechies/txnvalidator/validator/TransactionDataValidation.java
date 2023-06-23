package com.cloudtechies.txnvalidator.validator;

import com.cloudtechies.txnvalidator.config.TransactionValidatorProperties;
import com.cloudtechies.txnvalidator.db.TransactionReportRepoImpl;
import com.cloudtechies.txnvalidator.enums.TransactionStatus;
import com.cloudtechies.txnvalidator.exception.UnrecoverableException;
import com.cloudtechies.txnvalidator.kafka.KafkaOutputAdapter;
import com.cloudtechies.txnvalidator.model.TransactionReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class TransactionDataValidation {

    @Autowired
    TransactionReportRepoImpl transactionReportRepo;
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
            transactionReport.setPayloadId(UUID.fromString(payloadIds.get(i)));
            Optional<TransactionReport> existing = transactionReportRepo.getExistingTransactionReportIfAny(transactionReport.getPayloadId(),transactionReport.getTrnId());

            if(existing.isPresent()){
                log.info("Existing transaction found with TRN_ID and PAYLOAD_ID, will update with new data");
                TransactionReport existingTr = existing.get();
                UUID trRepId = existingTr.getTransactionReportId();
                Instant trCreateTs  = existingTr.getCreateTs();
                transactionReport.setTransactionReportId(trRepId);
                transactionReport.setCreateTs(trCreateTs);
                transactionReport.setUpdateTs(Instant.now());
            }else{
                transactionReport.setTransactionReportId(UUID.randomUUID());
                transactionReport.setCreateTs(Instant.now());
            }

            String outputTopicMessage;

            //if rejected reasons are null then it is valid transaction, otherwise invalid transaction
            List<String> rejectedReasons=getRejectedReasons(validator,transactionReport);
            if(rejectedReasons==null){
                transactionReport.setTxnStatus(TransactionStatus.PREENRICHVALID);
                //persist transaction after validation
                transactionReportRepo.persistTxns(transactionReport);
                outputTopicMessage=getSerializeMessage(transactionReport,objectMapper);
                kafkaOutputAdapter.sendMsgToKafka(outputTopicMessage,transactionValidatorProperties.getKafkaPrEnrValidTxnDataOutputTopic());
            }else{
                transactionReport.setTxnStatus(TransactionStatus.RJCT);
                transactionReport.setRjctReasons(rejectedReasons);
                //persist transaction after validation
                transactionReportRepo.persistTxns(transactionReport);
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

                String key = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
                Field field = null;
                try {
                    field = TransactionReport.class.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(transactionReport, null);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("Error while Setting property.");
                    rejectedReasons.add("Nullify Error!");
                }
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
