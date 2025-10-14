package org.example.demo_j5_asm1.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.example.demo_j5_asm1.entity.Condition;
import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.User;
import org.example.demo_j5_asm1.repository.BrandRepository;
import org.example.demo_j5_asm1.repository.CategoryRepository;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.repository.UserRepository;
import org.example.demo_j5_asm1.service.FileUploadService;
import org.example.demo_j5_asm1.service.PricingService;
import org.example.demo_j5_asm1.service.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepo;
    private final BrandRepository brandRepo;
    private final CategoryRepository categoryRepo;
    private final UserRepository userRepo;
    private final PricingService pricingService;
    private final FileUploadService fileUploadService;
    private final PromotionService promotionService;

    @GetMapping
    public String list(@RequestParam(required = false) String brand,
                       @RequestParam(required = false) String category,
                       @RequestParam(required = false, name = "q") String keyword,
                       @RequestParam(required = false) BigDecimal minPrice,
                       @RequestParam(required = false) BigDecimal maxPrice,
                       @RequestParam(required = false, defaultValue = "id") String sortBy,
                       @RequestParam(required = false, defaultValue = "asc") String sortDir,
                       @RequestParam(required = false, defaultValue = "0") int page,
                       @RequestParam(required = false, defaultValue = "10") int size,
                       Model model) {
        var spec = ProductSpecs.byFilters(brand, category, keyword, minPrice, maxPrice);
        
        // Tạo Sort object
        var sort = sortDir.equals("desc") ? 
            org.springframework.data.domain.Sort.by(sortBy).descending() : 
            org.springframework.data.domain.Sort.by(sortBy).ascending();
            
        // Tạo Pageable object
        var pageable = org.springframework.data.domain.PageRequest.of(page, size, sort);
        var productsPage = productRepo.findAll(spec, pageable);
        var products = productsPage.getContent();
        
        // Tính giá khuyến mãi cho từng sản phẩm
        for (var product : products) {
            var discountedPrice = promotionService.getDiscountedPrice(product);
            product.setDiscountedPrice(discountedPrice);
        }
        
        model.addAttribute("products", products);
        model.addAttribute("brands", brandRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("brand", brand);
        model.addAttribute("category", category);
        model.addAttribute("q", keyword);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("totalElements", productsPage.getTotalElements());
        model.addAttribute("hasNext", productsPage.hasNext());
        model.addAttribute("hasPrevious", productsPage.hasPrevious());
        return "products/list";
    }

    @GetMapping("/create")
    public String create(Model model, Authentication auth) {
        model.addAttribute("product", new Product());
        model.addAttribute("brands", brandRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("conditions", Condition.values());
        
        // Auto-select current user as seller if they have SELLER role
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            var currentUser = userRepo.findByUsername(username);
            if (currentUser.isPresent()) {
                model.addAttribute("currentUser", currentUser.get());
            }
        }
        
        return "products/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("product") Product p,
                       BindingResult br, Model model,
                       @RequestParam(required = false, defaultValue = "false") boolean applySuggest,
                       @RequestParam(required = false) MultipartFile imageFile) {
        if (br.hasErrors()) {
            model.addAttribute("brands", brandRepo.findAll());
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("users", userRepo.findAll());
            model.addAttribute("conditions", Condition.values());
            return "products/form";
        }
        
        // Handle file upload
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = fileUploadService.uploadFile(imageFile);
                p.setImageUrl(imageUrl);
            } catch (IOException e) {
                model.addAttribute("error", "Failed to upload image: " + e.getMessage());
                model.addAttribute("brands", brandRepo.findAll());
                model.addAttribute("categories", categoryRepo.findAll());
                model.addAttribute("users", userRepo.findAll());
                model.addAttribute("conditions", Condition.values());
                return "products/form";
            }
        }
        
        if (applySuggest) {
            BigDecimal suggested = pricingService.suggestPrice(p);
            p.setPrice(suggested);
        }
        productRepo.save(p);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model, Authentication auth) {
        var p = productRepo.findById(id).orElseThrow();
        
        // Kiểm tra quyền: chỉ admin hoặc seller sở hữu sản phẩm mới được sửa
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            var currentUser = userRepo.findByUsername(username);
            if (currentUser.isPresent()) {
                var user = currentUser.get();
                // Admin có thể sửa tất cả, seller chỉ sửa sản phẩm của mình
                if (user.getRole() != User.Role.ADMIN && 
                    (user.getRole() != User.Role.SELLER || !user.getId().equals(p.getSeller().getId()))) {
                    throw new RuntimeException("Bạn không có quyền sửa sản phẩm này");
                }
            }
        }
        
        model.addAttribute("product", p);
        model.addAttribute("brands", brandRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("conditions", Condition.values());
        return "products/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Authentication auth) {
        var product = productRepo.findById(id).orElseThrow();
        
        // Kiểm tra quyền: chỉ admin hoặc seller sở hữu sản phẩm mới được xóa
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            var currentUser = userRepo.findByUsername(username);
            if (currentUser.isPresent()) {
                var user = currentUser.get();
                // Admin có thể xóa tất cả, seller chỉ xóa sản phẩm của mình
                if (user.getRole() != User.Role.ADMIN && 
                    (user.getRole() != User.Role.SELLER || !user.getId().equals(product.getSeller().getId()))) {
                    throw new RuntimeException("Bạn không có quyền xóa sản phẩm này");
                }
            }
        }
        
        product.setActive(false); // Soft delete - chỉ đánh dấu inactive
        productRepo.save(product);
        return "redirect:/products";
    }

    /**
     * API endpoint để lấy gợi ý giá từ AI
     */
    @PostMapping("/ai-suggestion")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAIPricingSuggestion(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false, defaultValue = "0") BigDecimal currentPrice) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Đảm bảo currentPrice không null và positive
            BigDecimal price = currentPrice;
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                price = new BigDecimal("1000000"); // Giá mặc định
            }
            
            // Tạo product tạm để phân tích
            Product tempProduct = Product.builder()
                .title(title != null ? title : "Unknown Product")
                .price(price)
                .conditionGrade(condition != null ? Condition.valueOf(condition) : Condition.LIKE_NEW)
                .build();
            
            // Nếu có brand và category, set vào temp product
            if (brandId != null && brandId > 0) {
                var brand = brandRepo.findById(brandId);
                brand.ifPresent(tempProduct::setBrand);
            }
            if (categoryId != null && categoryId > 0) {
                var category = categoryRepo.findById(categoryId);
                category.ifPresent(tempProduct::setCategory);
            }
            
            // Nếu không có brand, tạo brand mặc định để tránh lỗi
            if (tempProduct.getBrand() == null) {
                var defaultBrand = brandRepo.findAll().stream().findFirst();
                defaultBrand.ifPresent(tempProduct::setBrand);
            }
            
            // Lấy gợi ý từ AI
            BigDecimal suggestedPrice = pricingService.suggestPrice(tempProduct);
            BigDecimal confidence = pricingService.getConfidence(tempProduct);
            String reasoning = pricingService.getReasoning(tempProduct);
            
            response.put("success", true);
            response.put("suggestedPrice", suggestedPrice);
            response.put("confidence", confidence);
            response.put("reasoning", reasoning);
            response.put("currentPrice", currentPrice);
            
            // Tính toán xu hướng
            if (currentPrice.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal difference = suggestedPrice.subtract(currentPrice);
                BigDecimal percentage = difference.divide(currentPrice, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
                
                response.put("difference", difference);
                response.put("percentage", percentage);
                response.put("trend", suggestedPrice.compareTo(currentPrice) > 0 ? "increasing" : "decreasing");
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Không thể tạo gợi ý giá: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
}