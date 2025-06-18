package com.example.friendo.Websocket.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatPrivateNotification {
    private String id;
    private String senderId;
    private String recipientId;
    private String content;
}
