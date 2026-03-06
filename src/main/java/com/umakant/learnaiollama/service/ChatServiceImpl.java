package com.umakant.learnaiollama.service;


import com.umakant.learnaiollama.entity.Tut;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/user-message.st")
    private Resource userMessage;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessage;

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

    public String promptFileTemplate(){
        return chatClient.prompt()
                .system(
                        system -> system
                                .text("You are a helpful coding assistant. You are an expert in coding."))
                .user(user -> user.text(userMessage).param("concept", "Spring framework validation"))
                .call()
                .content();
    }

    @Override
    public Flux<String> streamChat(String query) {
        return chatClient
                .prompt()
                .system(system -> system.text(this.systemMessage))
                .user(user -> user.text(this.userMessage).param("concept", query))
                .stream()
                .content();
    }
}
