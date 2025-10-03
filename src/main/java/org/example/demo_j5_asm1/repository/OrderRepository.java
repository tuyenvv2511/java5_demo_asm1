package org.example.demo_j5_asm1.repository;

import java.util.List;

import org.example.demo_j5_asm1.entity.Order;
import org.example.demo_j5_asm1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Tìm đơn hàng của người mua
    List<Order> findByBuyerOrderByCreatedAtDesc(User buyer);
    
    // Tìm đơn hàng của người bán
    List<Order> findBySellerOrderByCreatedAtDesc(User seller);
    
    // Tìm đơn hàng theo trạng thái
    List<Order> findByStatusOrderByCreatedAtDesc(Order.OrderStatus status);
    
    // Tìm đơn hàng theo nhiều trạng thái
    List<Order> findByStatusInOrderByCreatedAtDesc(List<Order.OrderStatus> statuses);
    
    // Đếm đơn hàng theo trạng thái
    long countByStatus(Order.OrderStatus status);
    
    // Tìm đơn hàng của sản phẩm
    @Query("SELECT o FROM Order o WHERE o.product.id = :productId ORDER BY o.createdAt DESC")
    List<Order> findByProductId(@Param("productId") Long productId);
}
