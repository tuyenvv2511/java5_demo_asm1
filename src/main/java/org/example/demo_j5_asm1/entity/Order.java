package org.example.demo_j5_asm1.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @NotNull
    @Positive
    private BigDecimal orderPrice;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @Column(length = 1000)
    private String notes; // Ghi chú từ người mua

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt; // Khi đơn hàng được cập nhật

    private LocalDateTime completedAt; // Khi đơn hàng hoàn thành

    public enum OrderStatus {
        PENDING,    // Chờ xác nhận
        CONFIRMED,  // Đã xác nhận
        SHIPPED,    // Đã gửi hàng
        DELIVERED,  // Đã giao hàng
        COMPLETED,  // Đã hoàn tất
        CANCELLED   // Đã hủy
    }
}
