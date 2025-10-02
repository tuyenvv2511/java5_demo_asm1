package org.example.demo_j5_asm1.repository;

import java.util.List;

import org.example.demo_j5_asm1.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select m from Message m where m.product.id = :productId order by m.sentAt")
    List<Message> findByProductId(@Param("productId") Long productId);
}