package com.example.prac.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExConfig {

    @Bean
    public ObjectMapper getObject(){
        return new ObjectMapper();
    }
}
