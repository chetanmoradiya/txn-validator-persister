package com.cloudtechies.txnvalidator.kafka;

import com.cloudtechies.txnvalidator.config.TransactionValidatorProperties;
import com.cloudtechies.txnvalidator.validator.TransactionDataValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TransactionDataTopicListener {

    @Autowired
    TransactionValidatorProperties transactionValidatorProperties;
    @Autowired
    TransactionDataValidation transactionDataValidation;

    @KafkaListener(topics = "#{transactionValidatorProperties.KafkaTxnDataInputTopic}",
                   groupId = "#{transactionValidatorProperties.kafkaConsumerGroupName}",
                   concurrency = "1")
    public void handleTxnInputEvent(@Payload List<String> messages,
                                    @Header(KafkaHeaders.BATCH_CONVERTED_HEADERS) List<Map<String,?>> batchConverterHeaders){
        log.info("message received:{}",messages);
        List<String> payloadIds =new ArrayList<>();
        List<Instant> payloadTS=new ArrayList<>();

        for(Map<String,?> item:batchConverterHeaders){
            payloadIds.add((String) item.get("PAYLOAD_ID"));
            payloadTS.add(Instant.ofEpochMilli((Long) item.get("PAYLOAD_TS")));
        }
        //validate transactions
        transactionDataValidation.validateData(messages,payloadIds);
    }
}
