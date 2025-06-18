package com.example.friendo.Websocket.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.Websocket.Model.ChatRoom;
import com.example.friendo.Websocket.Model.ChatRoomPrivate;
import com.example.friendo.Websocket.Repository.ChatRoomPrivateRepository;

@Service
public class ChatPrivateServices {
    private ChatRoomPrivateRepository chatRoomPrivateRepository;
    @Autowired
    public ChatPrivateServices(ChatRoomPrivateRepository ChatRoomPrivateRepository){
        this.chatRoomPrivateRepository = chatRoomPrivateRepository; 
    }
    public Optional<String> getChatRoomId(String senderId,String recipientId,boolean createRoomIfNotExist){
        return chatRoomPrivateRepository.findBySenderIdAndRecipientId(senderId, recipientId).map(ChatRoomPrivate::getChatId).or(() -> {
            if(createRoomIfNotExist){
                var chatId = createChatId(senderId, recipientId);
                return Optional.of(chatId);
            }
            return Optional.empty();
        });
    }
    private String createChatId(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId);

        ChatRoomPrivate senderRecipient = ChatRoomPrivate
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoomPrivate recipientSender = ChatRoomPrivate
                .builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomPrivateRepository.save(senderRecipient);
        chatRoomPrivateRepository.save(recipientSender);

        return chatId;
    }
}
