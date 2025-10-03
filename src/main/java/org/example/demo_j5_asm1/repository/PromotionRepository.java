package org.example.demo_j5_asm1.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.example.demo_j5_asm1.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    
    // Tìm khuyến mãi đang hoạt động của sản phẩm
    @Query("SELECT p FROM Promotion p WHERE p.product.id = :productId AND p.active = true " +
           "AND p.startDate <= :now AND p.endDate >= :now")
    List<Promotion> findActivePromotionsByProduct(@Param("productId") Long productId, 
                                                 @Param("now") LocalDateTime now);
    
    // Tìm tất cả khuyến mãi đang hoạt động
    @Query("SELECT p FROM Promotion p WHERE p.active = true " +
           "AND p.startDate <= :now AND p.endDate >= :now ORDER BY p.createdAt DESC")
    List<Promotion> findAllActivePromotions(@Param("now") LocalDateTime now);
    
    // Tìm khuyến mãi theo sản phẩm
    List<Promotion> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    // Đếm khuyến mãi đang hoạt động
    @Query("SELECT COUNT(p) FROM Promotion p WHERE p.active = true " +
           "AND p.startDate <= :now AND p.endDate >= :now")
    long countActivePromotions(@Param("now") LocalDateTime now);
}
