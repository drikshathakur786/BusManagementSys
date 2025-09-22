package com.example.demo.configration;



import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint clients connect to (fallback to SockJS)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // restrict in prod
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // messages whose destination starts with /app are routed to @MessageMapping
        registry.setApplicationDestinationPrefixes("/app");

        // simple broker for broadcasting to clients (topics/queues)
        registry.enableSimpleBroker("/topic", "/queue");
        // registry.setUserDestinationPrefix("/user"); // if using user-specific
    }
}
