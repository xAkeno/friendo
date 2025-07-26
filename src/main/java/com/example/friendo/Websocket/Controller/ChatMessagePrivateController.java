package com.example.friendo.Websocket.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.friendo.Websocket.Model.ChatMessagePrivate;
import com.example.friendo.Websocket.Model.ChatMessagee;
import com.example.friendo.Websocket.Model.ChatPrivateNotification;
import com.example.friendo.Websocket.Service.ChatMessagePrivateService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatMessagePrivateController {
    private ChatMessagePrivateService chatMessagePrivateService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatMessagePrivateController(ChatMessagePrivateService chatMessagePrivateService,SimpMessagingTemplate simpMessagingTemplate){
        this.chatMessagePrivateService = chatMessagePrivateService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat")
    // @PreAuthorize("isAuthenticated()")
    public void processMessage(@Payload ChatMessagePrivate chatMessagePrivate,Principal principal){
        ChatMessagePrivate savedMsg = chatMessagePrivateService.save(chatMessagePrivate);
        System.out.println("Sending private message to: " + chatMessagePrivate.getRecipientId());
        System.out.println("Principal (current user): " + savedMsg.getSenderId());
        System.out.println("Principal from session: " + principal.getName());

        ChatPrivateNotification notification = ChatPrivateNotification.builder()
            .id(savedMsg.getChatId())
            .senderId(savedMsg.getSenderId())
            .recipientId(savedMsg.getRecipientId())
            .content(savedMsg.getContent())
            .build();

        System.out.println("📦 Sending Chat Notification:");
        System.out.println("To: " + notification.getRecipientId());
        System.out.println("From: " + notification.getSenderId());
        System.out.println("Chat ID: " + notification.getId());
        System.out.println("Message: " + notification.getContent());

        simpMessagingTemplate.convertAndSendToUser(notification.getRecipientId(), "queue/messages", notification);
    }

    @GetMapping("messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessagePrivate>> findChatMessage(@PathVariable("senderId")String senderId,@PathVariable("recipientId")String recipientId){
        return ResponseEntity.ok().body(chatMessagePrivateService.findChatMessages(senderId, recipientId));
    }
}
