package org.example.demo_j5_asm1.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.example.demo_j5_asm1.entity.Order;
import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.User;
import org.example.demo_j5_asm1.repository.OrderRepository;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    
    @Transactional
    public Order createOrder(Long productId, User buyer, BigDecimal orderPrice, String notes) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        // Kiểm tra sản phẩm có đang bán không
        if (!product.getActive()) {
            throw new IllegalStateException("Product is not available for sale");
        }
        
        // Kiểm tra người mua không phải là người bán
        if (product.getSeller().getId().equals(buyer.getId())) {
            throw new IllegalStateException("You cannot buy your own product");
        }
        
        // Tạo đơn hàng
        Order order = Order.builder()
                .product(product)
                .buyer(buyer)
                .seller(product.getSeller())
                .orderPrice(orderPrice)
                .notes(notes)
                .status(Order.OrderStatus.PENDING)
                .build();
        
        Order savedOrder = orderRepo.save(order);
        log.info("Order created: {} for product: {} by buyer: {}", 
                savedOrder.getId(), product.getTitle(), buyer.getUsername());
        
        return savedOrder;
    }
    
    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus newStatus, User user) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        
        // Kiểm tra quyền: chỉ người bán mới có thể cập nhật trạng thái
        if (!order.getSeller().getId().equals(user.getId())) {
            throw new IllegalStateException("Only seller can update order status");
        }
        
        // Kiểm tra role: chỉ SELLER hoặc ADMIN mới có thể cập nhật trạng thái
        if (user.getRole() != User.Role.SELLER && user.getRole() != User.Role.ADMIN) {
            throw new IllegalStateException("Only sellers and admins can update order status");
        }
        
        // Kiểm tra chuyển đổi trạng thái tuần tự cho người bán
        validateStatusTransition(order.getStatus(), newStatus, true);
        
        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        
        // Nếu đơn hàng hoàn thành, cập nhật sản phẩm
        if (newStatus == Order.OrderStatus.DELIVERED) {
            order.setCompletedAt(LocalDateTime.now());
            // Có thể thêm logic để đánh dấu sản phẩm đã bán
        }
        
        Order updatedOrder = orderRepo.save(order);
        log.info("Order {} status updated to {} by {}", 
                orderId, newStatus, user.getUsername());
        
        return updatedOrder;
    }
    
    @Transactional
    public void cancelOrder(Long orderId, User user) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        
        // Kiểm tra quyền: người mua hoặc người bán có thể hủy
        if (!order.getBuyer().getId().equals(user.getId()) && 
            !order.getSeller().getId().equals(user.getId())) {
            throw new IllegalStateException("You can only cancel your own orders");
        }
        
        // Chỉ có thể hủy đơn hàng đang pending hoặc confirmed
        if (order.getStatus() != Order.OrderStatus.PENDING && 
            order.getStatus() != Order.OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Cannot cancel order with status: " + order.getStatus());
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepo.save(order);
        
        log.info("Order {} cancelled by {}", orderId, user.getUsername());
    }
    
    @Transactional
    public void completeOrder(Long orderId, User user) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        
        // Kiểm tra quyền: chỉ người mua mới có thể hoàn tất đơn hàng
        if (!order.getBuyer().getId().equals(user.getId())) {
            throw new IllegalStateException("Only buyer can complete the order");
        }
        
        // Kiểm tra role: chỉ BUYER hoặc ADMIN mới có thể hoàn tất đơn hàng
        if (user.getRole() != User.Role.BUYER && user.getRole() != User.Role.ADMIN) {
            throw new IllegalStateException("Only buyers and admins can complete orders");
        }
        
        // Chỉ có thể hoàn tất đơn hàng đã được giao (DELIVERED)
        if (order.getStatus() != Order.OrderStatus.DELIVERED) {
            throw new IllegalStateException("Order must be delivered before completion. Current status: " + order.getStatus());
        }
        
        order.setStatus(Order.OrderStatus.COMPLETED);
        order.setCompletedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepo.save(order);
        
        // Đánh dấu sản phẩm đã bán (không hiển thị trong danh sách nữa)
        Product product = order.getProduct();
        product.setActive(false);
        productRepo.save(product);
        
        log.info("Order {} completed by buyer {}, product {} marked as sold", 
                orderId, user.getUsername(), product.getId());
    }
    
    /**
     * Kiểm tra chuyển đổi trạng thái đơn hàng có hợp lệ không
     * @param currentStatus Trạng thái hiện tại
     * @param newStatus Trạng thái mới
     * @param isSellerUpdate true nếu là người bán cập nhật, false nếu là người mua
     */
    private void validateStatusTransition(Order.OrderStatus currentStatus, Order.OrderStatus newStatus, boolean isSellerUpdate) {
        if (isSellerUpdate) {
            // Người bán chỉ có thể chuyển: PENDING → CONFIRMED → SHIPPED → DELIVERED
            switch (currentStatus) {
                case PENDING -> {
                    if (newStatus != Order.OrderStatus.CONFIRMED && newStatus != Order.OrderStatus.CANCELLED) {
                        throw new IllegalStateException("From PENDING, seller can only change to CONFIRMED or CANCELLED");
                    }
                }
                case CONFIRMED -> {
                    if (newStatus != Order.OrderStatus.SHIPPED && newStatus != Order.OrderStatus.CANCELLED) {
                        throw new IllegalStateException("From CONFIRMED, seller can only change to SHIPPED or CANCELLED");
                    }
                }
                case SHIPPED -> {
                    if (newStatus != Order.OrderStatus.DELIVERED) {
                        throw new IllegalStateException("From SHIPPED, seller can only change to DELIVERED");
                    }
                }
                case DELIVERED, COMPLETED, CANCELLED -> 
                    throw new IllegalStateException("Cannot change status from " + currentStatus);
                default -> 
                    throw new IllegalStateException("Invalid current status: " + currentStatus);
            }
        } else {
            // Người mua chỉ có thể chuyển: DELIVERED → COMPLETED
            if (currentStatus == Order.OrderStatus.DELIVERED && newStatus == Order.OrderStatus.COMPLETED) {
                return; // Hợp lệ
            }
            throw new IllegalStateException("Buyer can only complete orders with DELIVERED status");
        }
    }
}
