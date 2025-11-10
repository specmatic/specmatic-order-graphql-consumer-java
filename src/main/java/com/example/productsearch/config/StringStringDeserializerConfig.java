package com.example.productsearch.config;

import com.example.productsearch.model.StrictStringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringStringDeserializerConfig {
    @Bean
    public SimpleModule strictStringDeserializerModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StrictStringDeserializer());
        return module;
    }
}
