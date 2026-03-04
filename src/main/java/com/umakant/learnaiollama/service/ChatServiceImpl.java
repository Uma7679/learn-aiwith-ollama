package com.umakant.learnaiollama.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatClient chatClient;
    public ChatServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String chat(String query) {
        return chatClient
                .prompt(query)
                .system("As an expert in cricket.")
                .call()
                .content();
    }
}
