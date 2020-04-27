package com.fyp.paymentservice.paymentservice.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ModelMapperConfigurations {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
