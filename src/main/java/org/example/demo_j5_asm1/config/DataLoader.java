package org.example.demo_j5_asm1.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.example.demo_j5_asm1.entity.Brand;
import org.example.demo_j5_asm1.entity.Category;
import org.example.demo_j5_asm1.entity.Condition;
import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.Sale;
import org.example.demo_j5_asm1.entity.User;
import org.example.demo_j5_asm1.repository.BrandRepository;
import org.example.demo_j5_asm1.repository.CategoryRepository;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.repository.SaleRepository;
import org.example.demo_j5_asm1.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final BrandRepository brandRepo;
    private final CategoryRepository categoryRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final SaleRepository saleRepo;

    @Override
    public void run(String... args) throws Exception {
        // Only load data if database is empty
        if (brandRepo.count() == 0) {
            loadBrands();
        }
        if (categoryRepo.count() == 0) {
            loadCategories();
        }
        if (userRepo.count() == 0) {
            loadUsers();
        }
        if (productRepo.count() == 0) {
            loadProducts();
        }
        if (saleRepo.count() == 0) {
            loadSales();
        }
    }

    private void loadBrands() {
        brandRepo.save(Brand.builder()
                .name("Nike")
                .country("US")
                .slug("nike")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Adidas")
                .country("DE")
                .slug("adidas")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Panini")
                .country("IT")
                .slug("panini")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Jordan")
                .country("US")
                .slug("jordan")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Converse")
                .country("US")
                .slug("converse")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());
    }

    private void loadCategories() {
        categoryRepo.save(Category.builder()
                .name("Sneaker")
                .slug("sneaker")
                .description("Giày sneaker các thương hiệu")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        categoryRepo.save(Category.builder()
                .name("Trading Card")
                .slug("card")
                .description("Thẻ giao dịch thể thao, game")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        categoryRepo.save(Category.builder()
                .name("Figure")
                .slug("figure")
                .description("Mô hình collectible")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        categoryRepo.save(Category.builder()
                .name("Apparel")
                .slug("apparel")
                .description("Quần áo thể thao")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        categoryRepo.save(Category.builder()
                .name("Accessories")
                .slug("accessories")
                .description("Phụ kiện thể thao")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());
    }

    private void loadUsers() {
        userRepo.save(User.builder()
                .username("admin")
                .email("admin@collectx.com")
                .password("admin123")
                .role(User.Role.ADMIN)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        userRepo.save(User.builder()
                .username("seller1")
                .email("seller1@collectx.com")
                .password("seller123")
                .role(User.Role.SELLER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        userRepo.save(User.builder()
                .username("buyer1")
                .email("buyer1@collectx.com")
                .password("buyer123")
                .role(User.Role.BUYER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        userRepo.save(User.builder()
                .username("collector")
                .email("collector@collectx.com")
                .password("collector123")
                .role(User.Role.BUYER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        userRepo.save(User.builder()
                .username("trader")
                .email("trader@collectx.com")
                .password("trader123")
                .role(User.Role.SELLER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());
    }

    private void loadProducts() {
        var nike = brandRepo.findBySlug("nike").orElseThrow();
        var adidas = brandRepo.findBySlug("adidas").orElseThrow();
        var panini = brandRepo.findBySlug("panini").orElseThrow();
        var jordan = brandRepo.findBySlug("jordan").orElseThrow();
        var converse = brandRepo.findBySlug("converse").orElseThrow();

        var sneaker = categoryRepo.findBySlug("sneaker").orElseThrow();
        var card = categoryRepo.findBySlug("card").orElseThrow();

        var seller1 = userRepo.findByUsername("seller1").orElseThrow();
        var collector = userRepo.findByUsername("collector").orElseThrow();
        var trader = userRepo.findByUsername("trader").orElseThrow();

        productRepo.save(Product.builder()
                .title("Nike Dunk Low Panda")
                .description("Size 42, fullbox")
                .imageUrl("https://example.com/nike-dunk.jpg")
                .price(new BigDecimal("4200000"))
                .conditionGrade(Condition.LIKE_NEW)
                .active(true)
                .brand(nike)
                .category(sneaker)
                .seller(seller1)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Adidas Samba OG")
                .description("Size 40, like new")
                .imageUrl("https://example.com/adidas-samba.jpg")
                .price(new BigDecimal("3200000"))
                .conditionGrade(Condition.LIKE_NEW)
                .active(true)
                .brand(adidas)
                .category(sneaker)
                .seller(seller1)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Panini Prizm Messi #15")
                .description("Pack fresh")
                .imageUrl("https://example.com/panini-messi.jpg")
                .price(new BigDecimal("2500000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(panini)
                .category(card)
                .seller(collector)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Jordan 1 Retro High")
                .description("Size 41, deadstock")
                .imageUrl("https://example.com/jordan1.jpg")
                .price(new BigDecimal("5500000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(jordan)
                .category(sneaker)
                .seller(trader)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Converse Chuck Taylor")
                .description("Size 39, vintage")
                .imageUrl("https://example.com/converse.jpg")
                .price(new BigDecimal("1800000"))
                .conditionGrade(Condition.GOOD)
                .active(true)
                .brand(converse)
                .category(sneaker)
                .seller(collector)
                .createdAt(LocalDateTime.now())
                .build());
    }

    private void loadSales() {
        var nikeProduct = productRepo.findByTitle("Nike Dunk Low Panda").orElse(null);
        var adidasProduct = productRepo.findByTitle("Adidas Samba OG").orElse(null);
        var paniniProduct = productRepo.findByTitle("Panini Prizm Messi #15").orElse(null);
        var converseProduct = productRepo.findByTitle("Converse Chuck Taylor").orElse(null);
        
        if (nikeProduct == null || adidasProduct == null || paniniProduct == null || converseProduct == null) {
            System.out.println("Some products not found, skipping sales loading");
            return;
        }

        var buyer1 = userRepo.findByUsername("buyer1").orElseThrow();
        var collector = userRepo.findByUsername("collector").orElseThrow();
        var trader = userRepo.findByUsername("trader").orElseThrow();

        saleRepo.save(Sale.builder()
                .product(nikeProduct)
                .buyer(buyer1)
                .seller(nikeProduct.getSeller())
                .soldPrice(new BigDecimal("4000000"))
                .build());

        saleRepo.save(Sale.builder()
                .product(adidasProduct)
                .buyer(collector)
                .seller(adidasProduct.getSeller())
                .soldPrice(new BigDecimal("3000000"))
                .build());

        saleRepo.save(Sale.builder()
                .product(paniniProduct)
                .buyer(trader)
                .seller(paniniProduct.getSeller())
                .soldPrice(new BigDecimal("2400000"))
                .build());

        saleRepo.save(Sale.builder()
                .product(converseProduct)
                .buyer(buyer1)
                .seller(converseProduct.getSeller())
                .soldPrice(new BigDecimal("1700000"))
                .build());
    }
}
