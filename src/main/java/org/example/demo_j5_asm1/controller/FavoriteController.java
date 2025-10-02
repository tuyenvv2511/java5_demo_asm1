package org.example.demo_j5_asm1.controller;

import org.example.demo_j5_asm1.entity.Favorite;
import org.example.demo_j5_asm1.repository.FavoriteRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteRepository favoriteRepo;

    @PostMapping
    public void add(@RequestParam Long userId, @RequestParam Long productId) {
        if (!favoriteRepo.existsByUserIdAndProductId(userId, productId)) {
            Favorite f = Favorite.builder().user(new org.example.demo_j5_asm1.entity.User()).product(new org.example.demo_j5_asm1.entity.Product()).build();
            f.getUser().setId(userId);
            f.getProduct().setId(productId);
            favoriteRepo.save(f);
        }
    }

    @DeleteMapping
    public void remove(@RequestParam Long userId, @RequestParam Long productId) {
        favoriteRepo.deleteByUserIdAndProductId(userId, productId);
    }
}