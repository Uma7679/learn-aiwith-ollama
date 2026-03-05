package com.umakant.learnaiollama.service;


import com.umakant.learnaiollama.entity.Tut;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatClient chatClient;
    public ChatServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Tut chat(String query) {
        Prompt prompt = new Prompt(query);
        var tutorial = chatClient
                .prompt(prompt)
                .call()
                .entity(Tut.class);

        return tutorial;
    }

    @Override
    public List<Tut> chatList(String query) {
        Prompt prompt = new Prompt(query);
        List<Tut> tutList = chatClient
                .prompt(prompt)
                .call()
                .entity(new ParameterizedTypeReference<List<Tut>>() {
                });

        return tutList;
    }
}
