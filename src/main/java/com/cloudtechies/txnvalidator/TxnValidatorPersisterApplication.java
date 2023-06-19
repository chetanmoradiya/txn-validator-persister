package com.cloudtechies.txnvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cloudtechies.txnvalidator")
public class TxnValidatorPersisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxnValidatorPersisterApplication.class, args);
	}

}
