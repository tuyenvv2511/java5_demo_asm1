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
    
    @GetMapping
    public String list(Model model) {
        // TODO: Lấy user hiện tại từ session/security
        // Tạm thời dùng user mặc định - buyer1
        User currentUser = createUser(1L, User.Role.BUYER);
        
        // Lấy đơn hàng của người mua (buyer1)
        List<Order> myOrders = orderRepo.findByBuyerOrderByCreatedAtDesc(currentUser);
        
        // Lấy đơn hàng của người bán (seller1) - cần tạo user seller
        User sellerUser = createUser(2L, User.Role.SELLER);
        List<Order> mySales = orderRepo.findBySellerOrderByCreatedAtDesc(sellerUser);
        
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
                           RedirectAttributes redirectAttributes) {
        try {
            // TODO: Lấy user hiện tại từ session/security
            User buyer = createUser(1L, User.Role.BUYER);
            
            Order order = orderService.createOrder(productId, buyer, orderPrice, notes);
            
            redirectAttributes.addFlashAttribute("success", 
                    "Đơn hàng đã được tạo thành công! ID: " + order.getId());
            return "redirect:/orders";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/" + productId + "/buy";
        }
    }
    
    @PostMapping("/{orderId}/status")
    public String updateStatus(@PathVariable Long orderId,
                             @RequestParam Order.OrderStatus status,
                             RedirectAttributes redirectAttributes) {
        try {
            // TODO: Lấy user hiện tại từ session/security
            // Tạm thời tạo user seller với role SELLER
            User currentUser = createUser(2L, User.Role.SELLER);
            
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
                            RedirectAttributes redirectAttributes) {
        try {
            // TODO: Lấy user hiện tại từ session/security
            // Tạm thời tạo user buyer với role BUYER
            User currentUser = createUser(1L, User.Role.BUYER);
            
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
                              RedirectAttributes redirectAttributes) {
        try {
            // TODO: Lấy user hiện tại từ session/security
            // Tạm thời tạo user buyer với role BUYER
            User currentUser = createUser(1L, User.Role.BUYER);
            
            orderService.completeOrder(orderId, currentUser);
            
            redirectAttributes.addFlashAttribute("success", 
                    "Đơn hàng đã được hoàn tất thành công!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/orders";
    }
    
    /**
     * Helper method để tạo user với role phù hợp
     * TODO: Thay thế bằng việc lấy user từ session/security
     */
    private User createUser(Long id, User.Role role) {
        User user = new User();
        user.setId(id);
        user.setRole(role);
        return user;
    }
}
