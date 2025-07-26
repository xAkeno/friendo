package com.example.friendo.Websocket.config;
import org.springframework.beans.factory.annotation.Autowired;
// Add to your WebSocketConfig or a new @Configuration class
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.user.SimpUserRegistry; // Make sure this import is correct
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal; // Import Principal

@Configuration
public class WebSocketMonitoringConfig {

    // SimpUserRegistry is automatically provided by Spring, no need to manually create
    // Just inject it
    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Bean
    public ApplicationListener<SessionConnectEvent> sessionConnectListener() {
        return event -> {
            String sessionId = event.getMessage().getHeaders().get("simpSessionId", String.class);
            Principal user = event.getUser();
            if (user != null) {
                System.out.println("✅ WebSocket CONNECTED (Listener): User=" + user.getName() + ", SessionId=" + sessionId);
            } else {
                System.out.println("✅ WebSocket CONNECTED (Listener): No User, SessionId=" + sessionId);
            }
            logActiveUsers(); // Log state after connect
        };
    }

    @Bean
    public ApplicationListener<SessionDisconnectEvent> sessionDisconnectListener() {
        return event -> {
            String sessionId = event.getMessage().getHeaders().get("simpSessionId", String.class);
            Principal user = event.getUser();
            if (user != null) {
                System.out.println("❌ WebSocket DISCONNECTED (Listener): User=" + user.getName() + ", SessionId=" + sessionId);
            } else {
                System.out.println("❌ WebSocket DISCONNECTED (Listener): No User, SessionId=" + sessionId);
            }
            logActiveUsers(); // Log state after disconnect
        };
    }

    private void logActiveUsers() {
        System.out.println("--- Current Active WebSocket Users ---");
        if (simpUserRegistry != null) { // Add a null check just in case for very early startup
            simpUserRegistry.getUsers().forEach(simpUser -> {
                System.out.println("User: " + simpUser.getName() + " (Session count: " + simpUser.getSessions().size() + ")");
                simpUser.getSessions().forEach(session -> {
                    System.out.println("  - Session ID: " + session.getId());
                });
            });
        } else {
            System.out.println("SimpUserRegistry not yet available.");
        }
        System.out.println("------------------------------------");
    }
}