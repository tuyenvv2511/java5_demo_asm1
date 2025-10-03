package org.example.demo_j5_asm1.controller;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
    private Long id;
    private String senderName;
    private String receiverName;
    private String productTitle;
    private String content;
    private LocalDateTime sentAt;
}
