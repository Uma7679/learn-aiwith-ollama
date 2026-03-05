package com.umakant.learnaiollama.service;


import com.umakant.learnaiollama.entity.Tut;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatClient chatClient;
    public ChatServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Tut chat(String query) {
        Prompt prompt = new Prompt(query);
        String queryString = "As an expert in coding and programming. Always write program in java. Now reply for this question : {query}";

        var tutorial = chatClient
                .prompt(prompt)
                .user(u -> u.text(queryString).param("query", query))
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

    public String chatTemplate(){
        PromptTemplate strTemplate =  PromptTemplate
                .builder()
                .template("What is {techname}? tell me example of {exampleName}")
                .build();

        // rendering the template
        String renderedMessage = strTemplate.render(Map.of(
                "techname", "Spring",
                "exampleName", "Spring Boot"
        ));

        Prompt prompt = new Prompt(renderedMessage);
        var content = chatClient.prompt(prompt)
                .call()
                .content();

        return content;
    }

    public String systemChatTemplate(){
        var systemPromptTemplate = SystemPromptTemplate.builder()
                .template("You are a helpful coding assistant. You are an expert in coding.")
                .build();

        var systemMessage = systemPromptTemplate.createMessage();

        var userPromptTemplate =  PromptTemplate
                .builder()
                .template("What is {techname}? tell me example of {exampleName}")
                .build();

        var userMessage = userPromptTemplate
                .createMessage(Map.of(
                        "techname", "Spring",
                        "exampleName", "Spring Exception"
                ));

        Prompt prompt = new Prompt(systemMessage, userMessage);

        return chatClient.prompt(prompt).call().content();
    }
}
