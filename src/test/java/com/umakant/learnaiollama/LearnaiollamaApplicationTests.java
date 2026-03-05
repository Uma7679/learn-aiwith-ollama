package com.umakant.learnaiollama;

import com.umakant.learnaiollama.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearnaiollamaApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    ChatService chatService;

    @Test
    void testTemplateRender(){
        System.out.println("Template Render");
        var output = chatService.chatTemplate();
        System.out.println(output);
    }

    @Test
    void testSystemTemplateRender(){
        System.out.println("System Template Render");
        var output = chatService.systemChatTemplate();
        System.out.println(output);
    }

}
