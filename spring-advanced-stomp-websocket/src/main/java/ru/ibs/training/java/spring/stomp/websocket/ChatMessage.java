package ru.ibs.training.java.spring.stomp.websocket;

public record ChatMessage(
    String content,
    String sender) {
}
