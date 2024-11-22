package com.tft.potato.rest.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String sender;
    private String content;
    private String activeType; // join, chat, leave

}
