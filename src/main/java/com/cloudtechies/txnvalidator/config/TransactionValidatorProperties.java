package com.cloudtechies.txnvalidator.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class TransactionValidatorProperties {

    @Value("${txn.validator.persister.kafkaClusterURL}")
    private String kafkaClusterURL;

    @Value("${txn.validator.persister.kafkaConsumerGroupName}")
    private String kafkaConsumerGroupName;

    @Value("${txn.validator.persister.KafkaTxnDataInputTopic}")
    private String KafkaTxnDataInputTopic;

    @Value("${txn.validator.persister.KafkaTxnDataOutputTopic}")
    private String KafkaTxnDataOutputTopic;

    @Value("${txn.validator.persister.restRetryCount}")
    private Integer restRetryCount;

    @Value("${txn.validator.persister.restBackoffTime}")
    private Integer restBackoffTime;

    @Value("${txn.validator.persister.maxPollSize}")
    private Integer maxPollSize;

}
