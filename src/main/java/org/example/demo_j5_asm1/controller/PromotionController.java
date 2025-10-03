package org.example.demo_j5_asm1.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.Promotion;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {
    
    private final PromotionService promotionService;
    private final ProductRepository productRepo;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("promotions", promotionService.getAllActivePromotions());
        return "promotions/list";
    }
    
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("products", productRepo.findByActiveTrue());
        model.addAttribute("promotionTypes", Promotion.PromotionType.values());
        return "promotions/form";
    }
    
    @PostMapping("/create")
    public String createPromotion(@RequestParam Long productId,
                                @RequestParam Promotion.PromotionType type,
                                @RequestParam BigDecimal discountValue,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                @RequestParam(required = false) String description,
                                RedirectAttributes redirectAttributes) {
        try {
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            
            Promotion promotion = promotionService.createPromotion(
                    product, type, discountValue, start, end, description);
            
            redirectAttributes.addFlashAttribute("success", 
                    "Khuyến mãi đã được tạo thành công! ID: " + promotion.getId());
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/promotions";
    }
    
    @PostMapping("/{id}/deactivate")
    public String deactivatePromotion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            promotionService.deactivatePromotion(id);
            redirectAttributes.addFlashAttribute("success", "Khuyến mãi đã được hủy kích hoạt thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/promotions";
    }
}
