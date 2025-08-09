package com.example.friendo.Websocket.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.friendo.Websocket.Model.ChatMessagePrivate;

@Repository
public interface ChatMessagePrivateRepository extends JpaRepository<ChatMessagePrivate,Integer>{

    List<ChatMessagePrivate> findByChatId(String s);
    @Query(value = "SELECT COUNT(*) FROM chat_message_private",nativeQuery = true)
    Optional<Integer> getTotalChat();
}
