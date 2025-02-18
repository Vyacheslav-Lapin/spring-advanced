package ru.ibs.training.java.spring.stomp.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  @MessageMapping("/sendMessage")
  @SendTo("/topic/messages")
  public ChatMessage sendMessage(ChatMessage chatMessage) {
    return chatMessage;
  }
}
