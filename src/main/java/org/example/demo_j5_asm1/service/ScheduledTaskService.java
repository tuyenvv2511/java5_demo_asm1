package org.example.demo_j5_asm1.service;

import java.time.LocalDateTime;
import java.util.List;

import org.example.demo_j5_asm1.entity.Promotion;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.repository.PromotionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ScheduledTaskService {
    private final PromotionRepository promotionRepo;
    private final ProductRepository productRepo;

    /**
     * Chạy mỗi 5 phút để kiểm tra và cập nhật trạng thái khuyến mãi
     */
    @Scheduled(fixedRate = 300000) // 5 phút
    public void updatePromotionStatus() {
        log.info("🔄 Starting promotion status update...");
        
        LocalDateTime now = LocalDateTime.now();
        
        // Kích hoạt khuyến mãi đã đến thời gian bắt đầu
        List<Promotion> upcomingPromotions = promotionRepo.findUpcomingPromotions(now);
        for (Promotion promotion : upcomingPromotions) {
            promotion.setActive(true);
            promotionRepo.save(promotion);
            log.info("✅ Activated promotion: {} for product: {}", 
                    promotion.getDescription(), promotion.getProduct().getTitle());
        }
        
        // Vô hiệu hóa khuyến mãi đã hết hạn
        List<Promotion> expiredPromotions = promotionRepo.findExpiredPromotions(now);
        for (Promotion promotion : expiredPromotions) {
            promotion.setActive(false);
            promotionRepo.save(promotion);
            log.info("❌ Deactivated expired promotion: {} for product: {}", 
                    promotion.getDescription(), promotion.getProduct().getTitle());
        }
        
        log.info("✅ Completed promotion status update. Activated: {}, Deactivated: {}", 
                upcomingPromotions.size(), expiredPromotions.size());
    }

    /**
     * Chạy mỗi ngày lúc 2:00 AM để thống kê và dọn dẹp dữ liệu
     */
    @Scheduled(cron = "0 0 2 * * ?") // Mỗi ngày lúc 2:00 AM
    public void dailyMaintenance() {
        log.info("🧹 Starting daily maintenance...");
        
        // Thống kê số lượng sản phẩm
        long totalProducts = productRepo.count();
        long activeProducts = productRepo.countByActiveTrue();
        
        log.info("📊 Product statistics: Total: {}, Active: {}, Inactive: {}", 
                totalProducts, activeProducts, totalProducts - activeProducts);
        
        // Thống kê khuyến mãi
        long totalPromotions = promotionRepo.count();
        long activePromotions = promotionRepo.countByActiveTrue();
        
        log.info("📊 Promotion statistics: Total: {}, Active: {}, Inactive: {}", 
                totalPromotions, activePromotions, totalPromotions - activePromotions);
        
        log.info("✅ Completed daily maintenance");
    }

    /**
     * Chạy mỗi giờ để kiểm tra sản phẩm cần cập nhật giá
     */
    @Scheduled(fixedRate = 3600000) // 1 giờ
    public void checkProductPricing() {
        log.info("💰 Checking products that need price updates...");
        
        // Có thể thêm logic kiểm tra sản phẩm cần cập nhật giá dựa trên xu hướng thị trường
        // Ví dụ: sản phẩm có giá quá cao hoặc quá thấp so với trung bình thị trường
        
        log.info("✅ Completed price check");
    }
    
    /**
     * Manual trigger for testing - can be called via REST endpoint
     */
    public void triggerPromotionUpdate() {
        log.info("🔧 Manual trigger - Starting promotion status update...");
        updatePromotionStatus();
        log.info("🔧 Manual trigger - Completed promotion status update");
    }
    
    /**
     * Manual trigger for testing - can be called via REST endpoint
     */
    public void triggerDailyMaintenance() {
        log.info("🔧 Manual trigger - Starting daily maintenance...");
        dailyMaintenance();
        log.info("🔧 Manual trigger - Completed daily maintenance");
    }
}
