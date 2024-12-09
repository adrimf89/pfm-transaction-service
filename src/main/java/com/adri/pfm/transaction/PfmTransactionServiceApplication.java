package com.adri.pfm.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.adri.pfm"})
public class PfmTransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfmTransactionServiceApplication.class, args);
	}

}
