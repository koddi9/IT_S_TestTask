package com.example.demo.utils;

import com.example.demo.models.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapperBean() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Article.class, new CustomSerializer());
        module.addDeserializer(Article.class, new CustomDeserializer());
        return mapper.registerModule(module);
    }
}
