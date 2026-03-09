package com.umakant.learnaiollama.service;

import com.umakant.learnaiollama.entity.Tut;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {
    Tut chat(String query);

    List<Tut> chatList(String query);

    public String chatTemplate();

    public String systemChatTemplate();

    public String promptFileTemplate();

    Flux<String> streamChat(String query);

    public String inMemoryChat(String query);

    public String chatWithUserId(String query, String userId);

    void saveData(List<String> list);
}
