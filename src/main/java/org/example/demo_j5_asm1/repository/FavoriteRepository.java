package org.example.demo_j5_asm1.repository;

import java.util.List;

import org.example.demo_j5_asm1.entity.Favorite;
import org.example.demo_j5_asm1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserIdAndProductId(Long userId, Long productId);
    
    // Method for price tracking service
    List<Favorite> findByProductAndPriceAlertEnabledTrue(Product product);
}