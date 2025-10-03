package org.example.demo_j5_asm1.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.example.demo_j5_asm1.entity.PriceHistory;
import org.example.demo_j5_asm1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    
    List<PriceHistory> findByProductOrderByCreatedAtDesc(Product product);
    
    @Query("SELECT ph FROM PriceHistory ph WHERE ph.product = :product AND ph.createdAt >= :since ORDER BY ph.createdAt DESC")
    List<PriceHistory> findByProductSince(@Param("product") Product product, @Param("since") LocalDateTime since);
    
    @Query("SELECT ph FROM PriceHistory ph WHERE ph.product = :product ORDER BY ph.createdAt DESC LIMIT 1")
    PriceHistory findLatestByProduct(@Param("product") Product product);
    
    List<PriceHistory> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end);
}
