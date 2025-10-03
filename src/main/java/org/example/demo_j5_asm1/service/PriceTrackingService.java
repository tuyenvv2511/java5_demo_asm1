package org.example.demo_j5_asm1.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.example.demo_j5_asm1.entity.Favorite;
import org.example.demo_j5_asm1.entity.PriceHistory;
import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.repository.FavoriteRepository;
import org.example.demo_j5_asm1.repository.PriceHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceTrackingService {
    
    private static final Logger log = LoggerFactory.getLogger(PriceTrackingService.class);
    
    // Constants for price change reasons
    private static final String REASON_PRICE_UPDATE = "REASON_PRICE_UPDATE";
    
    // Message templates for logging
    private static final String PRICE_ALERT_MESSAGE = "Price Alert: Product {} dropped to {} (Alert was set for {})";
    
    private final PriceHistoryRepository priceHistoryRepository;
    private final FavoriteRepository favoriteRepository;
    
    @Transactional
    public void trackPriceChange(Product product, BigDecimal newPrice) {
        BigDecimal previousPrice = product.getPrice();
        
        // Only track if price actually changed
        if (previousPrice != null && !previousPrice.equals(newPrice)) {
            PriceHistory priceHistory = PriceHistory.builder()
                .product(product)
                .price(newPrice)
                .previousPrice(previousPrice)
                .changeReason(REASON_PRICE_UPDATE)
                .build();
                
            priceHistoryRepository.save(priceHistory);
            
            // Check for price alerts
            checkPriceAlerts(product, newPrice);
        }
    }
    
    private void checkPriceAlerts(Product product, BigDecimal currentPrice) {
        List<Favorite> favorites = favoriteRepository.findByProductAndPriceAlertEnabledTrue(product);
        
        for (Favorite favorite : favorites) {
            if (favorite.getAlertPrice() != null && 
                currentPrice.compareTo(favorite.getAlertPrice()) <= 0) {
                // Price dropped to or below alert threshold
                sendPriceAlert(favorite, currentPrice);
            }
        }
    }
    
    private void sendPriceAlert(Favorite favorite, BigDecimal currentPrice) {
        // In a real application, you would send email/push notification here
        // For now, we'll just update the last alert sent time
        favorite.setLastAlertSent(LocalDateTime.now());
        favoriteRepository.save(favorite);
        
        log.info(PRICE_ALERT_MESSAGE, 
                favorite.getProduct().getTitle(), 
                currentPrice, 
                favorite.getAlertPrice());
    }
    
    public List<PriceHistory> getPriceHistory(Product product) {
        return priceHistoryRepository.findByProductOrderByCreatedAtDesc(product);
    }
    
    public List<PriceHistory> getRecentPriceChanges(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return priceHistoryRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(since, LocalDateTime.now());
    }
}
