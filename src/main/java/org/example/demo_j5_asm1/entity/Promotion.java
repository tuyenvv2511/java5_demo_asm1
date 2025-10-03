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
@Table(name = "promotion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PromotionType type = PromotionType.PERCENTAGE;

    @NotNull
    @Positive
    private BigDecimal discountValue; // Phần trăm hoặc số tiền giảm

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @Column(length = 500)
    private String description;

    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum PromotionType {
        PERCENTAGE,  // Giảm theo phần trăm
        FIXED_AMOUNT // Giảm số tiền cố định
    }

    // Tính giá sau khi giảm
    public BigDecimal calculateDiscountedPrice(BigDecimal originalPrice) {
        if (type == PromotionType.PERCENTAGE) {
            BigDecimal discountAmount = originalPrice.multiply(discountValue.divide(BigDecimal.valueOf(100)));
            return originalPrice.subtract(discountAmount);
        } else {
            return originalPrice.subtract(discountValue);
        }
    }

    // Kiểm tra khuyến mãi có đang hoạt động không
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return active && now.isAfter(startDate) && now.isBefore(endDate);
    }
}
