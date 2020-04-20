package com.fyp.walletservice.walletservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EntityScan("com.fyp.walletservice.walletservice.Entity")
@SpringBootApplication
@EnableEurekaClient
public class WalletserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletserviceApplication.class, args);
	}

}
