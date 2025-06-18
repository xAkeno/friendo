package com.example.friendo.Websocket.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    public void processMessage(@Payload ChatMessagePrivate chatMessagePrivate){
        ChatMessagePrivate savedMsg = chatMessagePrivateService.save(chatMessagePrivate);
        simpMessagingTemplate.convertAndSendToUser(chatMessagePrivate.getRecipientId(),
        "queue/messages", 
            ChatPrivateNotification.builder()
            .id(savedMsg.getId())
            .senderId(savedMsg
            .getSenderId())
            .recipientId(savedMsg
            .getRecipientId())
            .content(savedMsg.getContent())
            .build()
        );
    }

    @GetMapping("messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessagePrivate>> findChatMessage(@PathVariable("senderId")String senderId,@PathVariable("recipientId")String recipientId){
        return ResponseEntity.ok().body(chatMessagePrivateService.findChatMessages(senderId, recipientId));
    }
}
