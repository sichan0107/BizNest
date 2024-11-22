package com.tft.potato.rest.chat.controller;

import com.tft.potato.rest.chat.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

//    @GetMapping("/index")
//    public String index(){
//        return "index";
//    }

    //@MessageMapping("")

    @MessageMapping("/chat.sendMessage") // 클라이언트에서 /app/chat.sendMessage로 메시지 보낼 때 호출
    @SendTo("/topic/public") // 메시지를 /topic/public 구독자들에게 브로드캐스트
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser") // 사용자 접속 시 처리
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        chatMessage.setContent(chatMessage.getSender() + " joined!");
        return chatMessage;
    }

}
