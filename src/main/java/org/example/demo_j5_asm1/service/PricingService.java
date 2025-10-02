package org.example.demo_j5_asm1.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.repository.SaleRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingService {
    private final SaleRepository saleRepo;

    public BigDecimal suggestPrice(Product p) {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        BigDecimal avg = saleRepo.avgPriceLastDays(
                p.getBrand().getId(), p.getTitle(), from);
        if (avg == null) return p.getPrice() != null ? p.getPrice() : BigDecimal.ZERO;

        BigDecimal factor = switch (p.getConditionGrade()) {
            case NEW -> BigDecimal.valueOf(1.05);
            case LIKE_NEW -> BigDecimal.valueOf(1.00);
            case GOOD -> BigDecimal.valueOf(0.96);
            case USED -> BigDecimal.valueOf(0.92);
        };
        return avg.multiply(factor).setScale(0, RoundingMode.HALF_UP);
    }
}