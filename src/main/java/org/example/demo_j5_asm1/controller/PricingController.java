package org.example.demo_j5_asm1.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.Sale;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.repository.SaleRepository;
import org.example.demo_j5_asm1.service.PricingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pricing")
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    /**
     * Trang giới thiệu tính năng AI Pricing
     */
    @GetMapping("/features")
    public String pricingFeatures() {
        return "pricing/features";
    }

    /**
     * Dashboard phân tích giá cả - điểm nổi bật của ứng dụng
     */
    @GetMapping("/dashboard")
    public String pricingDashboard(Model model) {
        LocalDateTime from30Days = LocalDateTime.now().minusDays(30);
        
        // Thống kê tổng quan
        List<Sale> recentSales = saleRepository.findRecentSalesGlobally(from30Days);
        List<Object[]> topSellingProducts = saleRepository.getTopSellingProducts(from30Days);
        
        // Tính toán thống kê
        BigDecimal totalSalesValue = recentSales.stream()
                .map(Sale::getSoldPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal averagePrice = recentSales.isEmpty() ? BigDecimal.ZERO :
                totalSalesValue.divide(BigDecimal.valueOf(recentSales.size()), 2, java.math.RoundingMode.HALF_UP);
        
        model.addAttribute("totalSales", recentSales.size());
        model.addAttribute("totalValue", totalSalesValue);
        model.addAttribute("averagePrice", averagePrice);
        model.addAttribute("topSellingProducts", topSellingProducts);
        
        // Sản phẩm có xu hướng giá tăng/giảm
        List<Product> trendingProducts = getTrendingProducts();
        model.addAttribute("trendingProducts", trendingProducts);
        
        return "pricing/dashboard";
    }

    /**
     * Phân tích chi tiết giá cả cho một sản phẩm
     */
    @GetMapping("/analyze/{productId}")
    public String analyzeProduct(@PathVariable Long productId, Model model) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        PricingService.PricingAnalysis analysis = pricingService.analyzePricing(product);
        model.addAttribute("product", product);
        model.addAttribute("analysis", analysis);
        
        // Lịch sử bán hàng gần đây
        LocalDateTime from30Days = LocalDateTime.now().minusDays(30);
        List<Sale> recentSales = saleRepository.findRecentSales(
                product.getBrand().getId(), product.getTitle(), from30Days);
        model.addAttribute("recentSales", recentSales);
        
        return "pricing/analysis";
    }

    /**
     * API endpoint để lấy giá gợi ý (cho AJAX)
     */
    @GetMapping("/suggest/{productId}")
    public String suggestPrice(@PathVariable Long productId, Model model) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        BigDecimal suggestedPrice = pricingService.suggestPrice(product);
        PricingService.PricingAnalysis analysis = pricingService.analyzePricing(product);
        
        model.addAttribute("suggestedPrice", suggestedPrice);
        model.addAttribute("analysis", analysis);
        
        return "pricing/suggestion :: suggestion-content";
    }

    /**
     * Lấy danh sách sản phẩm có xu hướng giá thú vị
     */
    private List<Product> getTrendingProducts() {
        return productRepository.findAll().stream()
                .filter(p -> p.getActive())
                .map(p -> {
                    PricingService.PricingAnalysis analysis = pricingService.analyzePricing(p);
                    p.setPricingAnalysis(analysis); // Thêm analysis vào product
                    return p;
                })
                .filter(p -> p.getPricingAnalysis().getSalesCount() > 0)
                .sorted((p1, p2) -> {
                    // Sắp xếp theo độ tin cậy và xu hướng
                    int confidence1 = p1.getPricingAnalysis().getConfidence();
                    int confidence2 = p2.getPricingAnalysis().getConfidence();
                    
                    if (confidence1 != confidence2) {
                        return Integer.compare(confidence2, confidence1);
                    }
                    
                    // Nếu cùng confidence, ưu tiên xu hướng tăng
                    String trend1 = p1.getPricingAnalysis().getTrend();
                    String trend2 = p2.getPricingAnalysis().getTrend();
                    
                    if ("RISING".equals(trend1) && !"RISING".equals(trend2)) return -1;
                    if ("RISING".equals(trend2) && !"RISING".equals(trend1)) return 1;
                    
                    return 0;
                })
                .limit(10)
                .collect(Collectors.toList());
    }
}
