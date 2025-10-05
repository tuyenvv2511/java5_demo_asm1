package org.example.demo_j5_asm1.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.Sale;
import org.example.demo_j5_asm1.repository.SaleRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingService {
    private final SaleRepository saleRepo;

    /**
     * Gợi ý giá dựa trên dữ liệu bán hàng gần đây
     * Thuật toán AI thông minh phân tích xu hướng thị trường
     */
    public BigDecimal suggestPrice(Product product) {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        BigDecimal avgPrice = saleRepo.avgPriceLastDays(
                product.getBrand().getId(), product.getTitle(), from);
        
        if (avgPrice == null) {
            // Nếu không có dữ liệu lịch sử, sử dụng giá hiện tại với điều chỉnh nhẹ
            BigDecimal basePrice = product.getPrice() != null ? product.getPrice() : new BigDecimal("1000000");
            BigDecimal conditionFactor = getConditionFactor(product);
            return basePrice.multiply(conditionFactor).setScale(0, RoundingMode.HALF_UP);
        }

        // Hệ số điều chỉnh theo tình trạng sản phẩm
        BigDecimal conditionFactor = getConditionFactor(product);
        
        // Hệ số điều chỉnh theo xu hướng thị trường
        BigDecimal trendFactor = getTrendFactor(product.getBrand().getId(), product.getTitle());
        
        // Tính toán giá gợi ý cuối cùng
        BigDecimal suggestedPrice = avgPrice.multiply(conditionFactor).multiply(trendFactor);
        
        return suggestedPrice.setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * Lấy hệ số điều chỉnh theo tình trạng sản phẩm
     */
    private BigDecimal getConditionFactor(Product product) {
        return switch (product.getConditionGrade()) {
            case NEW -> BigDecimal.valueOf(1.05);      // Mới: +5%
            case LIKE_NEW -> BigDecimal.valueOf(1.00); // Gần như mới: giữ nguyên
            case GOOD -> BigDecimal.valueOf(0.96);     // Tốt: -4%
            case USED -> BigDecimal.valueOf(0.92);     // Đã sử dụng: -8%
        };
    }

    /**
     * Phân tích xu hướng thị trường dựa trên dữ liệu 60 ngày gần đây
     */
    private BigDecimal getTrendFactor(Long brandId, String title) {
        LocalDateTime from60Days = LocalDateTime.now().minusDays(60);
        LocalDateTime from30Days = LocalDateTime.now().minusDays(30);
        
        // Giá trung bình 30-60 ngày trước
        BigDecimal avgOld = saleRepo.avgPriceBetween(
                brandId, title, from60Days, from30Days);
        
        // Giá trung bình 30 ngày gần đây
        BigDecimal avgRecent = saleRepo.avgPriceLastDays(brandId, title, from30Days);
        
        if (avgOld == null || avgRecent == null) {
            return BigDecimal.valueOf(1.0); // Không có dữ liệu, giữ nguyên
        }
        
        // Tính tỷ lệ tăng/giảm
        BigDecimal trendRatio = avgRecent.divide(avgOld, 4, RoundingMode.HALF_UP);
        
        // Điều chỉnh xu hướng (chỉ áp dụng 50% để tránh biến động quá mạnh)
        BigDecimal trendFactor = BigDecimal.valueOf(1.0)
                .add(trendRatio.subtract(BigDecimal.valueOf(1.0))
                        .multiply(BigDecimal.valueOf(0.5)));
        
        // Giới hạn trong khoảng 0.9 - 1.1 (không quá ±10%)
        return trendFactor.max(BigDecimal.valueOf(0.9))
                          .min(BigDecimal.valueOf(1.1));
    }

    /**
     * Phân tích chi tiết giá cả cho một sản phẩm
     */
    public PricingAnalysis analyzePricing(Product product) {
        LocalDateTime from30Days = LocalDateTime.now().minusDays(30);
        LocalDateTime from60Days = LocalDateTime.now().minusDays(60);
        
        BigDecimal avg30Days = saleRepo.avgPriceLastDays(
                product.getBrand().getId(), product.getTitle(), from30Days);
        
        BigDecimal avg60Days = saleRepo.avgPriceBetween(
                product.getBrand().getId(), product.getTitle(), from60Days, from30Days);
        
        List<Sale> recentSales = saleRepo.findRecentSales(
                product.getBrand().getId(), product.getTitle(), from30Days);
        
        BigDecimal suggestedPrice = suggestPrice(product);
        BigDecimal currentPrice = product.getPrice();
        
        String trend = "STABLE";
        if (avg30Days != null && avg60Days != null) {
            if (avg30Days.compareTo(avg60Days) > 0) {
                trend = "RISING";
            } else if (avg30Days.compareTo(avg60Days) < 0) {
                trend = "FALLING";
            }
        }
        
        return PricingAnalysis.builder()
                .suggestedPrice(suggestedPrice)
                .currentPrice(currentPrice)
                .averagePrice30Days(avg30Days)
                .averagePrice60Days(avg60Days)
                .trend(trend)
                .salesCount(recentSales.size())
                .confidence(calculateConfidence(recentSales.size()))
                .build();
    }

    /**
     * Tính độ tin cậy của gợi ý giá (0-100%)
     */
    private int calculateConfidence(int salesCount) {
        if (salesCount >= 10) return 95;
        if (salesCount >= 5) return 85;
        if (salesCount >= 3) return 70;
        if (salesCount >= 1) return 50;
        return 20; // Ít dữ liệu
    }

    /**
     * Lấy độ tin cậy cho API suggestion
     */
    public BigDecimal getConfidence(Product product) {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        List<Sale> recentSales = saleRepo.findRecentSales(
                product.getBrand().getId(), product.getTitle(), from);
        
        int confidence = calculateConfidence(recentSales.size());
        return new BigDecimal(confidence);
    }

    /**
     * Lấy lý do gợi ý giá
     */
    public String getReasoning(Product product) {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        List<Sale> recentSales = saleRepo.findRecentSales(
                product.getBrand().getId(), product.getTitle(), from);
        
        if (recentSales.isEmpty()) {
            return "Dựa trên tình trạng sản phẩm và giá thị trường chung (ít dữ liệu lịch sử)";
        } else if (recentSales.size() < 3) {
            return "Dựa trên " + recentSales.size() + " giao dịch gần đây và tình trạng sản phẩm";
        } else {
            return "Dựa trên " + recentSales.size() + " giao dịch gần đây, xu hướng thị trường và tình trạng sản phẩm";
        }
    }

    /**
     * DTO cho phân tích giá cả
     */
    @lombok.Data
    @lombok.Builder
    public static class PricingAnalysis {
        private BigDecimal suggestedPrice;
        private BigDecimal currentPrice;
        private BigDecimal averagePrice30Days;
        private BigDecimal averagePrice60Days;
        private String trend; // RISING, FALLING, STABLE
        private int salesCount;
        private int confidence; // 0-100%
        
        public String getTrendIcon() {
            return switch (trend) {
                case "RISING" -> "📈";
                case "FALLING" -> "📉";
                default -> "➡️";
            };
        }
        
        public String getTrendText() {
            return switch (trend) {
                case "RISING" -> "Đang tăng";
                case "FALLING" -> "Đang giảm";
                default -> "Ổn định";
            };
        }
    }
}