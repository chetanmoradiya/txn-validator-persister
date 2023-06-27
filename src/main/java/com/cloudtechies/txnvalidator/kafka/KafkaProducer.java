package com.cloudtechies.txnvalidator.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void send(String topic, String message, Map<String,Object> headersMap){
        Headers headers=new RecordHeaders();
        MessageHeaders messageHeaders=new MessageHeaders(headersMap);
        new DefaultKafkaHeaderMapper().fromHeaders(messageHeaders,headers);

        ProducerRecord<String,String> producerRecord=new ProducerRecord<>(
                topic,0,"",message,headers);
        //log.info("Sending message to output topic.");

        kafkaTemplate.send(producerRecord);
    }
}
