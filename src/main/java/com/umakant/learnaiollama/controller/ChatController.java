package com.umakant.learnaiollama.controller;

import com.umakant.learnaiollama.entity.Tut;
import com.umakant.learnaiollama.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

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
    public ResponseEntity<Tut> chat(@RequestParam(value = "q") String query){
        String responseContent = ollamaChatClient.prompt(query).call().content();
        Tut chatResponse = chatService.chat(query);
        return ResponseEntity.ok(chatResponse);
    }

    @GetMapping("/chatlist")
    public ResponseEntity<List<Tut>> chatlist(@RequestParam(value = "q") String query){
        List<Tut> tutList = chatService.chatList(query);
        return ResponseEntity.ok(tutList);
    }

    @GetMapping("/stream-chat")
    public ResponseEntity<Flux<String>> streamChat(@RequestParam(value = "q") String query){
        return ResponseEntity.ok(chatService.streamChat(query));
    }
}
