package com.example.friendo.Websocket.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class ChatRoomPrivate {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;

    public String getChatId() {
        return chatId;
    }
}
