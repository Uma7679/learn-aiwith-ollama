package com.umakant.learnaiollama.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Bean(name = "ollamaChatClient")
    public ChatClient ollamaChatModel(OllamaChatModel ollamaChatModel){
        return ChatClient.builder(ollamaChatModel)
                .defaultOptions(OllamaChatOptions.builder()
                        .format("json")
                        .temperature(0.8)
                        .build())
                .build();
    }
}
