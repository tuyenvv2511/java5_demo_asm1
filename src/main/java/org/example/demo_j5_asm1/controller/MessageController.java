package org.example.demo_j5_asm1.controller;

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
    public Object getMessages(@RequestParam Long productId) {
        return messageRepo.findByProductId(productId);
    }
}