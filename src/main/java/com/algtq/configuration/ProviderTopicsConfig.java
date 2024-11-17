package com.algtq.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

@Configuration
public class ProviderTopicsConfig {

    @Bean
    public Map<String, String> providerTopics() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, String>> config = objectMapper.readValue(
                getClass().getClassLoader().getResourceAsStream("provider-topics.json"),
                Map.class
        );
        return config.get("provider_topics");
    }
}
