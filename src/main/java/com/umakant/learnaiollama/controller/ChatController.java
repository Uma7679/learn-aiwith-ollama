package com.umakant.learnaiollama.controller;

import com.umakant.learnaiollama.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ChatController {

    private final ChatClient ollamaChatClient;
    private final ChatService chatService;

    public ChatController(ChatClient ollamaChatClient, ChatService chatService) {
        this.ollamaChatClient = ollamaChatClient;
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam(value = "q") String query){
        String responseContent = ollamaChatClient.prompt(query).call().content();
        String chatResponse = chatService.chat(query);
        return ResponseEntity.ok(chatResponse);
    }
}
