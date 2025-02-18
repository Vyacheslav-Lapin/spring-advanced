package ru.ibs.training.java.spring.stomp.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @see <a href="https://apidog.com/blog/spring-boot-websocket/">Взято (и переработано) из статьи Ashley Innocent «Spring Boot WebSocket: Building Real-Time APIs»</a>
 */
@SpringBootApplication
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/chat")
            .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  public static void main(String... args) {
    SpringApplication.run(WebSocketConfig.class, args);
  }
}
