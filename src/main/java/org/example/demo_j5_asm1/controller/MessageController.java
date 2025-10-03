package org.example.demo_j5_asm1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.example.demo_j5_asm1.entity.Message;
import org.example.demo_j5_asm1.repository.MessageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepo;

    @GetMapping
    public List<MessageDTO> getMessages(@RequestParam Long productId) {
        List<Message> messages = messageRepo.findByProductId(productId);
        return messages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private MessageDTO convertToDTO(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .senderName(message.getSenderName() != null ? message.getSenderName() : "Guest")
                .receiverName(message.getReceiver() != null ? message.getReceiver().getUsername() : "Unknown")
                .productTitle(message.getProduct() != null ? message.getProduct().getTitle() : "Unknown Product")
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }
}