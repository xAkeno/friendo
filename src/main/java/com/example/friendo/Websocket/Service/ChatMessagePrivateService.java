package com.example.friendo.Websocket.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.friendo.Websocket.Model.ChatMessagePrivate;
import com.example.friendo.Websocket.Model.ChatRoomPrivate;
import com.example.friendo.Websocket.Repository.ChatMessagePrivateRepository;

@Service
public class ChatMessagePrivateService {
    private ChatMessagePrivateRepository chatMessagePrivateRepository;
    private ChatPrivateServices chatPrivateServices;
    public ChatMessagePrivateService(ChatMessagePrivateRepository chatMessagePrivateRepository,ChatPrivateServices chatPrivateServices){
        this.chatMessagePrivateRepository = chatMessagePrivateRepository;
        this.chatPrivateServices = chatPrivateServices;
    }

    public ChatMessagePrivate save(ChatMessagePrivate chatMessagePrivate){
        var chatId = chatPrivateServices.getChatRoomId(chatMessagePrivate.getSenderId(), chatMessagePrivate.getRecipientId(), true).orElseThrow();
        chatMessagePrivate.setChatId(chatId);
        chatMessagePrivateRepository.save(chatMessagePrivate);
        return chatMessagePrivate;
    }
    public List<ChatMessagePrivate> findChatMessages(String senderId,String recipientId){
        var chatId = chatPrivateServices.getChatRoomId(senderId, recipientId, false);
        return chatId.map(chatMessagePrivateRepository::findByChatId).orElse(new ArrayList<>());
    }
}
