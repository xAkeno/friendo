package com.example.friendo.Websocket.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.friendo.Websocket.Model.ChatRoom;
import com.example.friendo.Websocket.Model.ChatRoomPrivate;

@Repository
public interface ChatRoomPrivateRepository extends JpaRepository<ChatRoomPrivate,Integer>{
    Optional<ChatRoomPrivate> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
