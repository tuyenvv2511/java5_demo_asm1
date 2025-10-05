package org.example.demo_j5_asm1.controller;

import java.math.BigDecimal;
import java.util.List;

import org.example.demo_j5_asm1.entity.Order;
import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.User;
import org.example.demo_j5_asm1.repository.OrderRepository;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.service.OrderService;
import org.example.demo_j5_asm1.service.PromotionService;
import org.example.demo_j5_asm1.service.UserService;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final OrderService orderService;
    private final PromotionService promotionService;
    private final UserService userService;
    
    @GetMapping
    public String list(Authentication authentication, Model model) {
        User currentUser = getCurrentUser(authentication);
        
        List<Order> myOrders = List.of();
        List<Order> mySales = List.of();
        
        // Hiển thị đơn hàng theo vai trò
        if (currentUser.getRole() == User.Role.BUYER) {
            myOrders = orderRepo.findByBuyerOrderByCreatedAtDesc(currentUser);
        } else if (currentUser.getRole() == User.Role.SELLER) {
            mySales = orderRepo.findBySellerOrderByCreatedAtDesc(currentUser);
        } else if (currentUser.getRole() == User.Role.ADMIN) {
            // Admin có thể xem tất cả
            myOrders = orderRepo.findByBuyerOrderByCreatedAtDesc(currentUser);
            mySales = orderRepo.findBySellerOrderByCreatedAtDesc(currentUser);
        }
        
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("myOrders", myOrders);
        model.addAttribute("mySales", mySales);
        return "orders/list";
    }
    
    @GetMapping("/buy/{productId}")
    public String buyProduct(@PathVariable Long productId, Model model) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        // Kiểm tra sản phẩm có active không
        if (!product.getActive()) {
            throw new IllegalArgumentException("Product is no longer available");
        }
        
        // Tính giá khuyến mãi
        BigDecimal discountedPrice = promotionService.getDiscountedPrice(product);
        product.setDiscountedPrice(discountedPrice);
        
        model.addAttribute("product", product);
        return "orders/buy";
    }
    
    @PostMapping("/buy/{productId}")
    public String processBuy(@PathVariable Long productId,
                           @RequestParam BigDecimal orderPrice,
                           @RequestParam(required = false) String notes,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        try {
            User buyer = getCurrentUser(authentication);
            
            Order order = orderService.createOrder(productId, buyer, orderPrice, notes);
            
            redirectAttributes.addFlashAttribute("success", 
                    "Đơn hàng đã được tạo thành công! ID: " + order.getId());
            return "redirect:/orders";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/orders/buy/" + productId;
        }
    }
    
    @PostMapping("/{orderId}/status")
    public String updateStatus(@PathVariable Long orderId,
                             @RequestParam Order.OrderStatus status,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser(authentication);
            
            orderService.updateOrderStatus(orderId, status, currentUser);
            
            redirectAttributes.addFlashAttribute("success", 
                    "Trạng thái đơn hàng đã được cập nhật thành: " + status);
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/orders";
    }
    
    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser(authentication);
            
            orderService.cancelOrder(orderId, currentUser);
            
            redirectAttributes.addFlashAttribute("success", 
                    "Đơn hàng đã được hủy thành công!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/orders";
    }
    
    @PostMapping("/{orderId}/complete")
    public String completeOrder(@PathVariable Long orderId,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        try {
            User currentUser = getCurrentUser(authentication);
            
            orderService.completeOrder(orderId, currentUser);
            
            redirectAttributes.addFlashAttribute("success", 
                    "Đơn hàng đã được hoàn tất thành công!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/orders";
    }
    
    /**
     * Helper method để lấy user hiện tại từ authentication
     */
    private User getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}
