package com.umakant.learnaiollama.service;

import com.umakant.learnaiollama.entity.Tut;

import java.util.List;

public interface ChatService {
    Tut chat(String query);

    List<Tut> chatList(String query);
}
