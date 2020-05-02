package com.fyp.eholeservice.eholeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EholeserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EholeserviceApplication.class, args);
    }

}
