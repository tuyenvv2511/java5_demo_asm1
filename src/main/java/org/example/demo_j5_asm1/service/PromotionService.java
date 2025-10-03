package org.example.demo_j5_asm1.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.Promotion;
import org.example.demo_j5_asm1.repository.PromotionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {
    
    private final PromotionRepository promotionRepo;
    
    public List<Promotion> getActivePromotionsForProduct(Long productId) {
        return promotionRepo.findActivePromotionsByProduct(productId, LocalDateTime.now());
    }
    
    public List<Promotion> getAllActivePromotions() {
        return promotionRepo.findAllActivePromotions(LocalDateTime.now());
    }
    
    public Optional<Promotion> getBestPromotionForProduct(Long productId) {
        List<Promotion> promotions = getActivePromotionsForProduct(productId);
        if (promotions.isEmpty()) {
            return Optional.empty();
        }
        
        // Trả về khuyến mãi có giá trị giảm cao nhất
        return promotions.stream()
                .max((p1, p2) -> {
                    // So sánh dựa trên giá trị giảm thực tế
                    BigDecimal discount1 = calculateDiscountAmount(p1, new BigDecimal("1000000")); // Giá mẫu
                    BigDecimal discount2 = calculateDiscountAmount(p2, new BigDecimal("1000000"));
                    return discount1.compareTo(discount2);
                });
    }
    
    public BigDecimal calculateDiscountAmount(Promotion promotion, BigDecimal originalPrice) {
        if (promotion.getType() == Promotion.PromotionType.PERCENTAGE) {
            return originalPrice.multiply(promotion.getDiscountValue().divide(BigDecimal.valueOf(100)));
        } else {
            return promotion.getDiscountValue();
        }
    }
    
    public BigDecimal getDiscountedPrice(Product product) {
        Optional<Promotion> bestPromotion = getBestPromotionForProduct(product.getId());
        if (bestPromotion.isPresent()) {
            return bestPromotion.get().calculateDiscountedPrice(product.getPrice());
        }
        return product.getPrice();
    }
    
    @Transactional
    public Promotion createPromotion(Product product, Promotion.PromotionType type, 
                                   BigDecimal discountValue, LocalDateTime startDate, 
                                   LocalDateTime endDate, String description) {
        
        // Kiểm tra thời gian hợp lệ
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        
        // Kiểm tra giá trị giảm hợp lệ
        if (type == Promotion.PromotionType.PERCENTAGE && 
            (discountValue.compareTo(BigDecimal.ZERO) <= 0 || 
             discountValue.compareTo(BigDecimal.valueOf(100)) > 0)) {
            throw new IllegalArgumentException("Percentage discount must be between 0 and 100");
        }
        
        if (type == Promotion.PromotionType.FIXED_AMOUNT && 
            discountValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Fixed amount discount must be positive");
        }
        
        Promotion promotion = Promotion.builder()
                .product(product)
                .type(type)
                .discountValue(discountValue)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .active(true)
                .build();
        
        Promotion savedPromotion = promotionRepo.save(promotion);
        log.info("Created promotion: {} for product: {}", savedPromotion.getId(), product.getTitle());
        
        return savedPromotion;
    }
    
    @Transactional
    public void deactivatePromotion(Long promotionId) {
        Promotion promotion = promotionRepo.findById(promotionId)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));
        
        promotion.setActive(false);
        promotionRepo.save(promotion);
        
        log.info("Deactivated promotion: {}", promotionId);
    }
}
