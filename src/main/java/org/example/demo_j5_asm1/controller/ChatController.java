package org.example.demo_j5_asm1.controller;

import org.example.demo_j5_asm1.entity.Message;
import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.User;
import org.example.demo_j5_asm1.repository.MessageRepository;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.repository.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final MessageRepository messageRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    @MessageMapping("/chat.send")
    @SendTo("/topic/chat.{productId}")
    public ChatMessage send(ChatMessage msg) {
        // Save to db
        Product product = productRepo.findById(msg.getProductId()).orElse(null);
        User sender = userRepo.findById(msg.getSenderId()).orElse(null);
        User receiver = product != null ? product.getSeller() : null;
        if (product != null) {
            Message message = Message.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .product(product)
                    .content(msg.getContent())
                    .build();
            messageRepo.save(message);
        }
        return msg;
    }

    @Data
    public static class ChatMessage {
        private Long productId;
        private Long senderId;
        private String sender;
        private String content;
        
        public Long getProductId() { return productId; }
        public Long getSenderId() { return senderId; }
        public String getContent() { return content; }
    }
}