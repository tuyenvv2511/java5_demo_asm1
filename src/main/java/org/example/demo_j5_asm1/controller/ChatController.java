package org.example.demo_j5_asm1.controller;

import org.example.demo_j5_asm1.entity.Message;
import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.User;
import org.example.demo_j5_asm1.repository.MessageRepository;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.repository.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final MessageRepository messageRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void send(ChatMessage msg) {
        // Save to db
        Product product = productRepo.findById(msg.getProductId()).orElse(null);
        User receiver = product != null ? product.getSeller() : null;
        if (product != null) {
            Message message = Message.builder()
                    .sender(null) // Không cần sender vì chỉ dùng nickname
                    .receiver(receiver)
                    .product(product)
                    .content(msg.getContent())
                    .senderName(msg.getSender()) // Lưu nickname
                    .build();
            messageRepo.save(message);
            
            // Send to specific product chat room
            String destination = "/topic/chat." + msg.getProductId();
            messagingTemplate.convertAndSend(destination, msg);
        }
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