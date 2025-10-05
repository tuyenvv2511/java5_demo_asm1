package org.example.demo_j5_asm1.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.example.demo_j5_asm1.entity.Brand;
import org.example.demo_j5_asm1.entity.Category;
import org.example.demo_j5_asm1.entity.Condition;
import org.example.demo_j5_asm1.entity.Product;
import org.example.demo_j5_asm1.entity.Promotion;
import org.example.demo_j5_asm1.entity.Sale;
import org.example.demo_j5_asm1.entity.User;
import org.example.demo_j5_asm1.repository.BrandRepository;
import org.example.demo_j5_asm1.repository.CategoryRepository;
import org.example.demo_j5_asm1.repository.ProductRepository;
import org.example.demo_j5_asm1.repository.PromotionRepository;
import org.example.demo_j5_asm1.repository.SaleRepository;
import org.example.demo_j5_asm1.repository.UserRepository;
import org.example.demo_j5_asm1.service.SampleImageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PromotionRepository promotionRepo;
    private final SampleImageService sampleImageService;
    private final PasswordEncoder passwordEncoder;

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
            // Tạo hình ảnh mẫu trước khi load products
            sampleImageService.createAllSampleImages();
            loadProducts();
        }
        if (saleRepo.count() == 0) {
            loadSales();
        }
        if (promotionRepo.count() == 0) {
            loadPromotions();
        }
        
        // Cập nhật ảnh cho các sản phẩm mới (luôn chạy)
        updateProductImages();
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

        // Thêm nhiều thương hiệu mới
        brandRepo.save(Brand.builder()
                .name("Puma")
                .country("DE")
                .slug("puma")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("New Balance")
                .country("US")
                .slug("new-balance")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Vans")
                .country("US")
                .slug("vans")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Supreme")
                .country("US")
                .slug("supreme")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Off-White")
                .country("IT")
                .slug("off-white")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Topps")
                .country("US")
                .slug("topps")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Funko")
                .country("US")
                .slug("funko")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Bandai")
                .country("JP")
                .slug("bandai")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Hasbro")
                .country("US")
                .slug("hasbro")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Lego")
                .country("DK")
                .slug("lego")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Stussy")
                .country("US")
                .slug("stussy")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        brandRepo.save(Brand.builder()
                .name("Champion")
                .country("US")
                .slug("champion")
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
                .password(passwordEncoder.encode("admin123"))
                .role(User.Role.ADMIN)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        userRepo.save(User.builder()
                .username("seller1")
                .email("seller1@collectx.com")
                .password(passwordEncoder.encode("123456"))
                .role(User.Role.SELLER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        userRepo.save(User.builder()
                .username("buyer1")
                .email("buyer1@collectx.com")
                .password(passwordEncoder.encode("123456"))
                .role(User.Role.BUYER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        userRepo.save(User.builder()
                .username("collector")
                .email("collector@collectx.com")
                .password(passwordEncoder.encode("123456"))
                .role(User.Role.BUYER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build());

        userRepo.save(User.builder()
                .username("trader")
                .email("trader@collectx.com")
                .password(passwordEncoder.encode("123456"))
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
                .imageUrl("/uploads/images/nike_dunk_low_panda.jpg")
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
                .imageUrl("/uploads/images/adidas_samba_og.jpg")
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
                .imageUrl("/uploads/images/panini_prizm_messi__15.jpg")
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
                .imageUrl("/uploads/images/jordan_1_retro_high.jpg")
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
                .imageUrl("/uploads/images/converse_chuck_taylor.jpg")
                .price(new BigDecimal("1800000"))
                .conditionGrade(Condition.GOOD)
                .active(true)
                .brand(converse)
                .category(sneaker)
                .seller(collector)
                .createdAt(LocalDateTime.now())
                .build());

        // Lấy thêm brands và categories
        var puma = brandRepo.findByName("Puma").orElse(null);
        var newBalance = brandRepo.findByName("New Balance").orElse(null);
        var vans = brandRepo.findByName("Vans").orElse(null);
        var supreme = brandRepo.findByName("Supreme").orElse(null);
        var offWhite = brandRepo.findByName("Off-White").orElse(null);
        var topps = brandRepo.findByName("Topps").orElse(null);
        var funko = brandRepo.findByName("Funko").orElse(null);
        var bandai = brandRepo.findByName("Bandai").orElse(null);
        var hasbro = brandRepo.findByName("Hasbro").orElse(null);
        var lego = brandRepo.findByName("Lego").orElse(null);
        var stussy = brandRepo.findByName("Stussy").orElse(null);
        var champion = brandRepo.findByName("Champion").orElse(null);

        var figure = categoryRepo.findByName("Figure").orElse(null);
        var apparel = categoryRepo.findByName("Apparel").orElse(null);
        var accessories = categoryRepo.findByName("Accessories").orElse(null);

        // Thêm nhiều sản phẩm Sneaker mới
        productRepo.save(Product.builder()
                .title("Puma RS-X Reinvention")
                .description("Size 42, limited edition")
                .price(new BigDecimal("2800000"))
                .conditionGrade(Condition.LIKE_NEW)
                .active(true)
                .brand(puma)
                .category(sneaker)
                .seller(trader)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("New Balance 550 White")
                .description("Size 40, fullbox")
                .price(new BigDecimal("3200000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(newBalance)
                .category(sneaker)
                .seller(seller1)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Vans Old Skool Black")
                .description("Size 39, vintage")
                .price(new BigDecimal("1200000"))
                .conditionGrade(Condition.GOOD)
                .active(true)
                .brand(vans)
                .category(sneaker)
                .seller(collector)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Nike Air Max 90")
                .description("Size 41, infrared")
                .price(new BigDecimal("3500000"))
                .conditionGrade(Condition.LIKE_NEW)
                .active(true)
                .brand(nike)
                .category(sneaker)
                .seller(trader)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Adidas Ultraboost 22")
                .description("Size 42, white")
                .price(new BigDecimal("4000000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(adidas)
                .category(sneaker)
                .seller(seller1)
                .createdAt(LocalDateTime.now())
                .build());

        // Thêm sản phẩm Trading Card
        productRepo.save(Product.builder()
                .title("Topps Chrome Kobe Bryant Rookie")
                .description("PSA 9, 1996")
                .price(new BigDecimal("15000000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(topps)
                .category(card)
                .seller(trader)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Panini Prizm Luka Doncic RC")
                .description("Silver prizm, 2018")
                .price(new BigDecimal("8500000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(panini)
                .category(card)
                .seller(collector)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Topps Chrome Tom Brady Rookie")
                .description("BGS 9.5, 2000")
                .price(new BigDecimal("12000000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(topps)
                .category(card)
                .seller(trader)
                .createdAt(LocalDateTime.now())
                .build());

        // Thêm sản phẩm Figure
        productRepo.save(Product.builder()
                .title("Funko Pop Batman #01")
                .description("Vaulted, mint condition")
                .price(new BigDecimal("800000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(funko)
                .category(figure)
                .seller(collector)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Bandai Gundam RX-78-2")
                .description("Master Grade, unbuilt")
                .price(new BigDecimal("1200000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(bandai)
                .category(figure)
                .seller(seller1)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Hasbro Transformers Optimus Prime")
                .description("Vintage 1984, complete")
                .price(new BigDecimal("2500000"))
                .conditionGrade(Condition.GOOD)
                .active(true)
                .brand(hasbro)
                .category(figure)
                .seller(trader)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Lego Star Wars Death Star")
                .description("Set 10188, complete with box")
                .price(new BigDecimal("18000000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(lego)
                .category(figure)
                .seller(collector)
                .createdAt(LocalDateTime.now())
                .build());

        // Thêm sản phẩm Apparel
        productRepo.save(Product.builder()
                .title("Supreme Box Logo Hoodie")
                .description("FW20, size L, black")
                .price(new BigDecimal("8500000"))
                .conditionGrade(Condition.LIKE_NEW)
                .active(true)
                .brand(supreme)
                .category(apparel)
                .seller(trader)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Off-White Industrial Belt")
                .description("Yellow, 200cm")
                .price(new BigDecimal("6500000"))
                .conditionGrade(Condition.LIKE_NEW)
                .active(true)
                .brand(offWhite)
                .category(apparel)
                .seller(seller1)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Stussy World Tour Tee")
                .description("Size M, white, vintage")
                .price(new BigDecimal("1800000"))
                .conditionGrade(Condition.GOOD)
                .active(true)
                .brand(stussy)
                .category(apparel)
                .seller(collector)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Champion Reverse Weave Hoodie")
                .description("Size XL, grey")
                .price(new BigDecimal("2200000"))
                .conditionGrade(Condition.LIKE_NEW)
                .active(true)
                .brand(champion)
                .category(apparel)
                .seller(seller1)
                .createdAt(LocalDateTime.now())
                .build());

        // Thêm sản phẩm Accessories
        productRepo.save(Product.builder()
                .title("Supreme TNF Mountain Tee")
                .description("FW18, size S")
                .price(new BigDecimal("3500000"))
                .conditionGrade(Condition.LIKE_NEW)
                .active(true)
                .brand(supreme)
                .category(accessories)
                .seller(trader)
                .createdAt(LocalDateTime.now())
                .build());

        productRepo.save(Product.builder()
                .title("Nike Air Jordan Cap")
                .description("Snapback, black/red")
                .price(new BigDecimal("800000"))
                .conditionGrade(Condition.NEW)
                .active(true)
                .brand(jordan)
                .category(accessories)
                .seller(seller1)
                .createdAt(LocalDateTime.now())
                .build());
    }

    private void loadSales() {
        var nikeProduct = productRepo.findByTitle("Nike Dunk Low Panda").orElse(null);
        var adidasProduct = productRepo.findByTitle("Adidas Samba OG").orElse(null);
        var paniniProduct = productRepo.findByTitle("Panini Prizm Messi #15").orElse(null);
        var jordanProduct = productRepo.findByTitle("Jordan 1 Retro High").orElse(null);
        var converseProduct = productRepo.findByTitle("Converse Chuck Taylor").orElse(null);
        
        if (nikeProduct == null || adidasProduct == null || paniniProduct == null || 
            jordanProduct == null || converseProduct == null) {
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

        // Thêm nhiều dữ liệu sales với các ngày khác nhau để AI có thể phân tích xu hướng
        LocalDateTime now = LocalDateTime.now();
        
        // Sales cho Nike Dunk Low Panda - 30 ngày gần đây
        saleRepo.save(Sale.builder()
                .product(nikeProduct)
                .buyer(buyer1)
                .seller(nikeProduct.getSeller())
                .soldPrice(new BigDecimal("3200000"))
                .soldAt(now.minusDays(5))
                .build());

        saleRepo.save(Sale.builder()
                .product(nikeProduct)
                .buyer(collector)
                .seller(nikeProduct.getSeller())
                .soldPrice(new BigDecimal("3100000"))
                .soldAt(now.minusDays(10))
                .build());

        saleRepo.save(Sale.builder()
                .product(nikeProduct)
                .buyer(trader)
                .seller(nikeProduct.getSeller())
                .soldPrice(new BigDecimal("3300000"))
                .soldAt(now.minusDays(15))
                .build());

        saleRepo.save(Sale.builder()
                .product(nikeProduct)
                .buyer(buyer1)
                .seller(nikeProduct.getSeller())
                .soldPrice(new BigDecimal("3150000"))
                .soldAt(now.minusDays(20))
                .build());

        saleRepo.save(Sale.builder()
                .product(nikeProduct)
                .buyer(collector)
                .seller(nikeProduct.getSeller())
                .soldPrice(new BigDecimal("3250000"))
                .soldAt(now.minusDays(25))
                .build());

        // Sales cho Adidas Samba OG - 30 ngày gần đây
        saleRepo.save(Sale.builder()
                .product(adidasProduct)
                .buyer(trader)
                .seller(adidasProduct.getSeller())
                .soldPrice(new BigDecimal("2800000"))
                .soldAt(now.minusDays(3))
                .build());

        saleRepo.save(Sale.builder()
                .product(adidasProduct)
                .buyer(buyer1)
                .seller(adidasProduct.getSeller())
                .soldPrice(new BigDecimal("2750000"))
                .soldAt(now.minusDays(8))
                .build());

        saleRepo.save(Sale.builder()
                .product(adidasProduct)
                .buyer(collector)
                .seller(adidasProduct.getSeller())
                .soldPrice(new BigDecimal("2850000"))
                .soldAt(now.minusDays(12))
                .build());

        saleRepo.save(Sale.builder()
                .product(adidasProduct)
                .buyer(trader)
                .seller(adidasProduct.getSeller())
                .soldPrice(new BigDecimal("2700000"))
                .soldAt(now.minusDays(18))
                .build());

        saleRepo.save(Sale.builder()
                .product(adidasProduct)
                .buyer(buyer1)
                .seller(adidasProduct.getSeller())
                .soldPrice(new BigDecimal("2900000"))
                .soldAt(now.minusDays(22))
                .build());

        // Sales cho Jordan 1 Retro High - 30 ngày gần đây
        saleRepo.save(Sale.builder()
                .product(jordanProduct)
                .buyer(collector)
                .seller(jordanProduct.getSeller())
                .soldPrice(new BigDecimal("4500000"))
                .soldAt(now.minusDays(4))
                .build());

        saleRepo.save(Sale.builder()
                .product(jordanProduct)
                .buyer(trader)
                .seller(jordanProduct.getSeller())
                .soldPrice(new BigDecimal("4600000"))
                .soldAt(now.minusDays(9))
                .build());

        saleRepo.save(Sale.builder()
                .product(jordanProduct)
                .buyer(buyer1)
                .seller(jordanProduct.getSeller())
                .soldPrice(new BigDecimal("4400000"))
                .soldAt(now.minusDays(14))
                .build());

        saleRepo.save(Sale.builder()
                .product(jordanProduct)
                .buyer(collector)
                .seller(jordanProduct.getSeller())
                .soldPrice(new BigDecimal("4700000"))
                .soldAt(now.minusDays(19))
                .build());

        saleRepo.save(Sale.builder()
                .product(jordanProduct)
                .buyer(trader)
                .seller(jordanProduct.getSeller())
                .soldPrice(new BigDecimal("4550000"))
                .soldAt(now.minusDays(24))
                .build());

        // Sales cho Converse Chuck Taylor - 30 ngày gần đây
        saleRepo.save(Sale.builder()
                .product(converseProduct)
                .buyer(buyer1)
                .seller(converseProduct.getSeller())
                .soldPrice(new BigDecimal("1600000"))
                .soldAt(now.minusDays(6))
                .build());

        saleRepo.save(Sale.builder()
                .product(converseProduct)
                .buyer(collector)
                .seller(converseProduct.getSeller())
                .soldPrice(new BigDecimal("1650000"))
                .soldAt(now.minusDays(11))
                .build());

        saleRepo.save(Sale.builder()
                .product(converseProduct)
                .buyer(trader)
                .seller(converseProduct.getSeller())
                .soldPrice(new BigDecimal("1550000"))
                .soldAt(now.minusDays(16))
                .build());

        saleRepo.save(Sale.builder()
                .product(converseProduct)
                .buyer(buyer1)
                .seller(converseProduct.getSeller())
                .soldPrice(new BigDecimal("1680000"))
                .soldAt(now.minusDays(21))
                .build());

        saleRepo.save(Sale.builder()
                .product(converseProduct)
                .buyer(collector)
                .seller(converseProduct.getSeller())
                .soldPrice(new BigDecimal("1620000"))
                .soldAt(now.minusDays(26))
                .build());

        // Sales cho Panini Prizm Messi #15 - 30 ngày gần đây
        saleRepo.save(Sale.builder()
                .product(paniniProduct)
                .buyer(trader)
                .seller(paniniProduct.getSeller())
                .soldPrice(new BigDecimal("2500000"))
                .soldAt(now.minusDays(2))
                .build());

        saleRepo.save(Sale.builder()
                .product(paniniProduct)
                .buyer(collector)
                .seller(paniniProduct.getSeller())
                .soldPrice(new BigDecimal("2450000"))
                .soldAt(now.minusDays(7))
                .build());

        saleRepo.save(Sale.builder()
                .product(paniniProduct)
                .buyer(buyer1)
                .seller(paniniProduct.getSeller())
                .soldPrice(new BigDecimal("2550000"))
                .soldAt(now.minusDays(13))
                .build());

        saleRepo.save(Sale.builder()
                .product(paniniProduct)
                .buyer(trader)
                .seller(paniniProduct.getSeller())
                .soldPrice(new BigDecimal("2350000"))
                .soldAt(now.minusDays(17))
                .build());

        saleRepo.save(Sale.builder()
                .product(paniniProduct)
                .buyer(collector)
                .seller(paniniProduct.getSeller())
                .soldPrice(new BigDecimal("2600000"))
                .soldAt(now.minusDays(23))
                .build());

        // Thêm một số sales cũ hơn (30-60 ngày trước) để tính xu hướng
        saleRepo.save(Sale.builder()
                .product(nikeProduct)
                .buyer(buyer1)
                .seller(nikeProduct.getSeller())
                .soldPrice(new BigDecimal("3000000"))
                .soldAt(now.minusDays(35))
                .build());

        saleRepo.save(Sale.builder()
                .product(adidasProduct)
                .buyer(trader)
                .seller(adidasProduct.getSeller())
                .soldPrice(new BigDecimal("2600000"))
                .soldAt(now.minusDays(40))
                .build());

        saleRepo.save(Sale.builder()
                .product(jordanProduct)
                .buyer(collector)
                .seller(jordanProduct.getSeller())
                .soldPrice(new BigDecimal("4200000"))
                .soldAt(now.minusDays(45))
                .build());

        // Thêm sales cho các sản phẩm mới
        // Sales cho Puma RS-X Reinvention
        var pumaProduct = productRepo.findByTitle("Puma RS-X Reinvention").orElse(null);
        if (pumaProduct != null) {
            saleRepo.save(Sale.builder()
                    .product(pumaProduct)
                    .buyer(buyer1)
                    .seller(pumaProduct.getSeller())
                    .soldPrice(new BigDecimal("2700000"))
                    .soldAt(now.minusDays(7))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(pumaProduct)
                    .buyer(trader)
                    .seller(pumaProduct.getSeller())
                    .soldPrice(new BigDecimal("2850000"))
                    .soldAt(now.minusDays(14))
                    .build());
        }

        // Sales cho New Balance 550
        var nbProduct = productRepo.findByTitle("New Balance 550 White").orElse(null);
        if (nbProduct != null) {
            saleRepo.save(Sale.builder()
                    .product(nbProduct)
                    .buyer(collector)
                    .seller(nbProduct.getSeller())
                    .soldPrice(new BigDecimal("3100000"))
                    .soldAt(now.minusDays(6))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(nbProduct)
                    .buyer(buyer1)
                    .seller(nbProduct.getSeller())
                    .soldPrice(new BigDecimal("3250000"))
                    .soldAt(now.minusDays(13))
                    .build());
        }

        // Sales cho Vans Old Skool
        var vansProduct = productRepo.findByTitle("Vans Old Skool Black").orElse(null);
        if (vansProduct != null) {
            saleRepo.save(Sale.builder()
                    .product(vansProduct)
                    .buyer(trader)
                    .seller(vansProduct.getSeller())
                    .soldPrice(new BigDecimal("1150000"))
                    .soldAt(now.minusDays(9))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(vansProduct)
                    .buyer(collector)
                    .seller(vansProduct.getSeller())
                    .soldPrice(new BigDecimal("1250000"))
                    .soldAt(now.minusDays(16))
                    .build());
        }

        // Sales cho Nike Air Max 90
        var airMaxProduct = productRepo.findByTitle("Nike Air Max 90").orElse(null);
        if (airMaxProduct != null) {
            saleRepo.save(Sale.builder()
                    .product(airMaxProduct)
                    .buyer(buyer1)
                    .seller(airMaxProduct.getSeller())
                    .soldPrice(new BigDecimal("3400000"))
                    .soldAt(now.minusDays(5))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(airMaxProduct)
                    .buyer(trader)
                    .seller(airMaxProduct.getSeller())
                    .soldPrice(new BigDecimal("3600000"))
                    .soldAt(now.minusDays(11))
                    .build());
        }

        // Sales cho Adidas Ultraboost
        var ultraboostProduct = productRepo.findByTitle("Adidas Ultraboost 22").orElse(null);
        if (ultraboostProduct != null) {
            saleRepo.save(Sale.builder()
                    .product(ultraboostProduct)
                    .buyer(collector)
                    .seller(ultraboostProduct.getSeller())
                    .soldPrice(new BigDecimal("3900000"))
                    .soldAt(now.minusDays(4))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(ultraboostProduct)
                    .buyer(buyer1)
                    .seller(ultraboostProduct.getSeller())
                    .soldPrice(new BigDecimal("4100000"))
                    .soldAt(now.minusDays(10))
                    .build());
        }

        // Sales cho Trading Cards
        var kobeCard = productRepo.findByTitle("Topps Chrome Kobe Bryant Rookie").orElse(null);
        if (kobeCard != null) {
            saleRepo.save(Sale.builder()
                    .product(kobeCard)
                    .buyer(trader)
                    .seller(kobeCard.getSeller())
                    .soldPrice(new BigDecimal("14800000"))
                    .soldAt(now.minusDays(8))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(kobeCard)
                    .buyer(collector)
                    .seller(kobeCard.getSeller())
                    .soldPrice(new BigDecimal("15200000"))
                    .soldAt(now.minusDays(15))
                    .build());
        }

        var lukaCard = productRepo.findByTitle("Panini Prizm Luka Doncic RC").orElse(null);
        if (lukaCard != null) {
            saleRepo.save(Sale.builder()
                    .product(lukaCard)
                    .buyer(buyer1)
                    .seller(lukaCard.getSeller())
                    .soldPrice(new BigDecimal("8300000"))
                    .soldAt(now.minusDays(6))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(lukaCard)
                    .buyer(trader)
                    .seller(lukaCard.getSeller())
                    .soldPrice(new BigDecimal("8700000"))
                    .soldAt(now.minusDays(12))
                    .build());
        }

        // Sales cho Figures
        var batmanPop = productRepo.findByTitle("Funko Pop Batman #01").orElse(null);
        if (batmanPop != null) {
            saleRepo.save(Sale.builder()
                    .product(batmanPop)
                    .buyer(collector)
                    .seller(batmanPop.getSeller())
                    .soldPrice(new BigDecimal("780000"))
                    .soldAt(now.minusDays(3))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(batmanPop)
                    .buyer(buyer1)
                    .seller(batmanPop.getSeller())
                    .soldPrice(new BigDecimal("820000"))
                    .soldAt(now.minusDays(9))
                    .build());
        }

        var gundam = productRepo.findByTitle("Bandai Gundam RX-78-2").orElse(null);
        if (gundam != null) {
            saleRepo.save(Sale.builder()
                    .product(gundam)
                    .buyer(trader)
                    .seller(gundam.getSeller())
                    .soldPrice(new BigDecimal("1150000"))
                    .soldAt(now.minusDays(7))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(gundam)
                    .buyer(collector)
                    .seller(gundam.getSeller())
                    .soldPrice(new BigDecimal("1250000"))
                    .soldAt(now.minusDays(14))
                    .build());
        }

        // Sales cho Apparel
        var supremeHoodie = productRepo.findByTitle("Supreme Box Logo Hoodie").orElse(null);
        if (supremeHoodie != null) {
            saleRepo.save(Sale.builder()
                    .product(supremeHoodie)
                    .buyer(trader)
                    .seller(supremeHoodie.getSeller())
                    .soldPrice(new BigDecimal("8300000"))
                    .soldAt(now.minusDays(5))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(supremeHoodie)
                    .buyer(buyer1)
                    .seller(supremeHoodie.getSeller())
                    .soldPrice(new BigDecimal("8700000"))
                    .soldAt(now.minusDays(11))
                    .build());
        }

        var offWhiteBelt = productRepo.findByTitle("Off-White Industrial Belt").orElse(null);
        if (offWhiteBelt != null) {
            saleRepo.save(Sale.builder()
                    .product(offWhiteBelt)
                    .buyer(collector)
                    .seller(offWhiteBelt.getSeller())
                    .soldPrice(new BigDecimal("6300000"))
                    .soldAt(now.minusDays(8))
                    .build());
            
            saleRepo.save(Sale.builder()
                    .product(offWhiteBelt)
                    .buyer(trader)
                    .seller(offWhiteBelt.getSeller())
                    .soldPrice(new BigDecimal("6700000"))
                    .soldAt(now.minusDays(16))
                    .build());
        }
    }

    private void loadPromotions() {
        // Lấy các sản phẩm để tạo khuyến mãi
        var nikeProduct = productRepo.findByTitle("Nike Dunk Low Panda").orElse(null);
        var adidasProduct = productRepo.findByTitle("Adidas Samba OG").orElse(null);
        var paniniProduct = productRepo.findByTitle("Panini Prizm Messi #15").orElse(null);
        var jordanProduct = productRepo.findByTitle("Jordan 1 Retro High").orElse(null);
        var converseProduct = productRepo.findByTitle("Converse Chuck Taylor").orElse(null);
        
        if (nikeProduct == null || adidasProduct == null || paniniProduct == null || 
            jordanProduct == null || converseProduct == null) {
            System.out.println("Some products not found, skipping promotions loading");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        
        // 1. Khuyến mãi Nike - Giảm 15% trong 7 ngày
        promotionRepo.save(Promotion.builder()
                .product(nikeProduct)
                .type(Promotion.PromotionType.PERCENTAGE)
                .discountValue(new BigDecimal("15"))
                .startDate(now.minusDays(1)) // Bắt đầu từ hôm qua
                .endDate(now.plusDays(6))    // Kết thúc sau 6 ngày nữa
                .description("Khuyến mãi Black Friday - Giảm 15% cho Nike Dunk Low Panda")
                .active(true)
                .createdAt(now)
                .build());

        // 2. Khuyến mãi Adidas - Giảm 500,000 VNĐ trong 10 ngày
        promotionRepo.save(Promotion.builder()
                .product(adidasProduct)
                .type(Promotion.PromotionType.FIXED_AMOUNT)
                .discountValue(new BigDecimal("500000"))
                .startDate(now.minusDays(2)) // Bắt đầu từ 2 ngày trước
                .endDate(now.plusDays(8))    // Kết thúc sau 8 ngày nữa
                .description("Flash Sale - Giảm ngay 500,000 VNĐ cho Adidas Samba OG")
                .active(true)
                .createdAt(now)
                .build());

        // 3. Khuyến mãi Panini - Giảm 20% trong 5 ngày
        promotionRepo.save(Promotion.builder()
                .product(paniniProduct)
                .type(Promotion.PromotionType.PERCENTAGE)
                .discountValue(new BigDecimal("20"))
                .startDate(now)              // Bắt đầu từ hôm nay
                .endDate(now.plusDays(5))    // Kết thúc sau 5 ngày
                .description("Weekend Special - Giảm 20% cho thẻ Panini Prizm Messi")
                .active(true)
                .createdAt(now)
                .build());

        // 4. Khuyến mãi Jordan - Giảm 1,000,000 VNĐ trong 14 ngày
        promotionRepo.save(Promotion.builder()
                .product(jordanProduct)
                .type(Promotion.PromotionType.FIXED_AMOUNT)
                .discountValue(new BigDecimal("1000000"))
                .startDate(now.plusDays(1))  // Bắt đầu từ ngày mai
                .endDate(now.plusDays(15))   // Kết thúc sau 15 ngày
                .description("Premium Sale - Giảm 1,000,000 VNĐ cho Jordan 1 Retro High")
                .active(true)
                .createdAt(now)
                .build());

        // 5. Khuyến mãi Converse - Giảm 25% trong 3 ngày
        promotionRepo.save(Promotion.builder()
                .product(converseProduct)
                .type(Promotion.PromotionType.PERCENTAGE)
                .discountValue(new BigDecimal("25"))
                .startDate(now.plusDays(2))  // Bắt đầu sau 2 ngày
                .endDate(now.plusDays(5))    // Kết thúc sau 5 ngày
                .description("Limited Time Offer - Giảm 25% cho Converse Chuck Taylor vintage")
                .active(true)
                .createdAt(now)
                .build());

        // Thêm nhiều khuyến mãi mới cho các sản phẩm mới
        var pumaProduct = productRepo.findByTitle("Puma RS-X Reinvention").orElse(null);
        var nbProduct = productRepo.findByTitle("New Balance 550 White").orElse(null);
        var vansProduct = productRepo.findByTitle("Vans Old Skool Black").orElse(null);
        var airMaxProduct = productRepo.findByTitle("Nike Air Max 90").orElse(null);
        var ultraboostProduct = productRepo.findByTitle("Adidas Ultraboost 22").orElse(null);
        var kobeCard = productRepo.findByTitle("Topps Chrome Kobe Bryant Rookie").orElse(null);
        var lukaCard = productRepo.findByTitle("Panini Prizm Luka Doncic RC").orElse(null);
        var batmanPop = productRepo.findByTitle("Funko Pop Batman #01").orElse(null);
        var gundam = productRepo.findByTitle("Bandai Gundam RX-78-2").orElse(null);
        var supremeHoodie = productRepo.findByTitle("Supreme Box Logo Hoodie").orElse(null);
        var offWhiteBelt = productRepo.findByTitle("Off-White Industrial Belt").orElse(null);

        // Khuyến mãi đang hoạt động (Active Promotions)
        if (pumaProduct != null) {
            promotionRepo.save(Promotion.builder()
                    .product(pumaProduct)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("12"))
                    .startDate(now.minusDays(1))
                    .endDate(now.plusDays(4))
                    .description("🔥 Hot Deal - Giảm 12% cho Puma RS-X Reinvention")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (nbProduct != null) {
            promotionRepo.save(Promotion.builder()
                    .product(nbProduct)
                    .type(Promotion.PromotionType.FIXED_AMOUNT)
                    .discountValue(new BigDecimal("300000"))
                    .startDate(now.minusDays(2))
                    .endDate(now.plusDays(6))
                    .description("💥 Flash Sale - Giảm ngay 300K cho New Balance 550")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (vansProduct != null) {
            promotionRepo.save(Promotion.builder()
                    .product(vansProduct)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("8"))
                    .startDate(now.minusDays(3))
                    .endDate(now.plusDays(7))
                    .description("⭐ Weekend Special - Giảm 8% cho Vans Old Skool")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (airMaxProduct != null) {
            promotionRepo.save(Promotion.builder()
                    .product(airMaxProduct)
                    .type(Promotion.PromotionType.FIXED_AMOUNT)
                    .discountValue(new BigDecimal("250000"))
                    .startDate(now.minusDays(1))
                    .endDate(now.plusDays(9))
                    .description("🚀 Limited Time - Giảm 250K cho Nike Air Max 90")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (ultraboostProduct != null) {
            promotionRepo.save(Promotion.builder()
                    .product(ultraboostProduct)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("18"))
                    .startDate(now.minusDays(4))
                    .endDate(now.plusDays(3))
                    .description("🎯 Mega Sale - Giảm 18% cho Adidas Ultraboost 22")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (kobeCard != null) {
            promotionRepo.save(Promotion.builder()
                    .product(kobeCard)
                    .type(Promotion.PromotionType.FIXED_AMOUNT)
                    .discountValue(new BigDecimal("1000000"))
                    .startDate(now.minusDays(2))
                    .endDate(now.plusDays(5))
                    .description("🏆 Legendary Card - Giảm 1M cho Kobe Bryant Rookie")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (lukaCard != null) {
            promotionRepo.save(Promotion.builder()
                    .product(lukaCard)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("15"))
                    .startDate(now.minusDays(1))
                    .endDate(now.plusDays(6))
                    .description("⭐ Rising Star - Giảm 15% cho Luka Doncic RC")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (batmanPop != null) {
            promotionRepo.save(Promotion.builder()
                    .product(batmanPop)
                    .type(Promotion.PromotionType.FIXED_AMOUNT)
                    .discountValue(new BigDecimal("50000"))
                    .startDate(now.minusDays(3))
                    .endDate(now.plusDays(4))
                    .description("🦇 Hero Collection - Giảm 50K cho Batman Funko Pop")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (gundam != null) {
            promotionRepo.save(Promotion.builder()
                    .product(gundam)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("10"))
                    .startDate(now.minusDays(2))
                    .endDate(now.plusDays(8))
                    .description("🤖 Anime Collection - Giảm 10% cho Gundam RX-78-2")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (supremeHoodie != null) {
            promotionRepo.save(Promotion.builder()
                    .product(supremeHoodie)
                    .type(Promotion.PromotionType.FIXED_AMOUNT)
                    .discountValue(new BigDecimal("800000"))
                    .startDate(now.minusDays(1))
                    .endDate(now.plusDays(7))
                    .description("🔥 Supreme Drop - Giảm 800K cho Box Logo Hoodie")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        if (offWhiteBelt != null) {
            promotionRepo.save(Promotion.builder()
                    .product(offWhiteBelt)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("12"))
                    .startDate(now.minusDays(4))
                    .endDate(now.plusDays(3))
                    .description("⚡ Streetwear Sale - Giảm 12% cho Off-White Belt")
                    .active(true)
                    .createdAt(now)
                    .build());
        }

        // Khuyến mãi đã kết thúc (Expired Promotions)
        if (pumaProduct != null) {
            promotionRepo.save(Promotion.builder()
                    .product(pumaProduct)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("20"))
                    .startDate(now.minusDays(20))
                    .endDate(now.minusDays(15))
                    .description("🎉 Grand Opening Sale - Giảm 20% cho Puma RS-X (Đã kết thúc)")
                    .active(false)
                    .createdAt(now.minusDays(20))
                    .build());
        }

        if (nbProduct != null) {
            promotionRepo.save(Promotion.builder()
                    .product(nbProduct)
                    .type(Promotion.PromotionType.FIXED_AMOUNT)
                    .discountValue(new BigDecimal("400000"))
                    .startDate(now.minusDays(25))
                    .endDate(now.minusDays(18))
                    .description("🏃‍♂️ Running Week - Giảm 400K cho New Balance (Đã kết thúc)")
                    .active(false)
                    .createdAt(now.minusDays(25))
                    .build());
        }

        if (kobeCard != null) {
            promotionRepo.save(Promotion.builder()
                    .product(kobeCard)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("25"))
                    .startDate(now.minusDays(30))
                    .endDate(now.minusDays(22))
                    .description("🏀 Legend Tribute - Giảm 25% cho Kobe Card (Đã kết thúc)")
                    .active(false)
                    .createdAt(now.minusDays(30))
                    .build());
        }

        if (supremeHoodie != null) {
            promotionRepo.save(Promotion.builder()
                    .product(supremeHoodie)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("30"))
                    .startDate(now.minusDays(35))
                    .endDate(now.minusDays(28))
                    .description("🔥 Black Friday - Giảm 30% cho Supreme Hoodie (Đã kết thúc)")
                    .active(false)
                    .createdAt(now.minusDays(35))
                    .build());
        }

        if (gundam != null) {
            promotionRepo.save(Promotion.builder()
                    .product(gundam)
                    .type(Promotion.PromotionType.FIXED_AMOUNT)
                    .discountValue(new BigDecimal("150000"))
                    .startDate(now.minusDays(28))
                    .endDate(now.minusDays(21))
                    .description("🤖 Gundam Anniversary - Giảm 150K cho RX-78-2 (Đã kết thúc)")
                    .active(false)
                    .createdAt(now.minusDays(28))
                    .build());
        }

        // Khuyến mãi sắp bắt đầu (Upcoming Promotions)
        if (vansProduct != null) {
            promotionRepo.save(Promotion.builder()
                    .product(vansProduct)
                    .type(Promotion.PromotionType.PERCENTAGE)
                    .discountValue(new BigDecimal("15"))
                    .startDate(now.plusDays(2))
                    .endDate(now.plusDays(12))
                    .description("🎨 Art Week - Giảm 15% cho Vans (Sắp bắt đầu)")
                    .active(false)
                    .createdAt(now)
                    .build());
        }

        if (lukaCard != null) {
            promotionRepo.save(Promotion.builder()
                    .product(lukaCard)
                    .type(Promotion.PromotionType.FIXED_AMOUNT)
                    .discountValue(new BigDecimal("500000"))
                    .startDate(now.plusDays(5))
                    .endDate(now.plusDays(15))
                    .description("🏀 Playoff Season - Giảm 500K cho Luka Card (Sắp bắt đầu)")
                    .active(false)
                    .createdAt(now)
                    .build());
        }

        System.out.println("Loaded " + (5 + 22) + " sample promotions");
    }

    private void updateProductImages() {
        // Lấy tất cả sản phẩm và cập nhật ảnh
        var allProducts = productRepo.findAll();
        
        for (var product : allProducts) {
            String newImageUrl = null;
            
            // Cập nhật ảnh dựa trên tên sản phẩm
            switch (product.getTitle()) {
                // Sneakers
                case "Puma RS-X Reinvention":
                    newImageUrl = "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500&h=500&fit=crop&auto=format";
                    break;
                case "New Balance 550 White":
                    newImageUrl = "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Vans Old Skool Black":
                    newImageUrl = "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Nike Air Max 90":
                    newImageUrl = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Adidas Ultraboost 22":
                    newImageUrl = "https://images.unsplash.com/photo-1560769629-975ec94e6a86?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Nike Dunk Low Panda":
                    newImageUrl = "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Adidas Samba OG":
                    newImageUrl = "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Jordan 1 Retro High":
                    newImageUrl = "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Converse Chuck Taylor":
                    newImageUrl = "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500&h=500&fit=crop&auto=format";
                    break;
                    
                // Trading Cards
                case "Topps Chrome Kobe Bryant Rookie":
                    newImageUrl = "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Panini Prizm Luka Doncic RC":
                    newImageUrl = "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Topps Chrome Tom Brady Rookie":
                    newImageUrl = "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Panini Prizm Messi #15":
                    newImageUrl = "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500&h=500&fit=crop&auto=format";
                    break;
                    
                // Figures & Toys
                case "Funko Pop Batman #01":
                    newImageUrl = "/uploads/images/funko_pop_batman__01.jpg";
                    break;
                case "Bandai Gundam RX-78-2":
                    newImageUrl = "/uploads/images/bandai_gundam_rx_78_2.jpg";
                    break;
                case "Hasbro Transformers Optimus Prime":
                    newImageUrl = "/uploads/images/hasbro_transformers_optimus_prime.jpg";
                    break;
                case "Lego Star Wars AT-AT":
                    newImageUrl = "/uploads/images/lego_star_wars_at_at.jpg";
                    break;
                case "Lego Star Wars Death Star":
                    newImageUrl = "/uploads/images/lego_star_wars_death_star.jpg";
                    break;
                    
                // Apparel
                case "Supreme Box Logo Hoodie":
                    newImageUrl = "https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Stussy World Tour Tee":
                    newImageUrl = "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Champion Reverse Weave Hoodie":
                    newImageUrl = "https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Supreme TNF Mountain Tee":
                    newImageUrl = "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500&h=500&fit=crop&auto=format";
                    break;
                    
                // Accessories
                case "Off-White Industrial Belt":
                    newImageUrl = "https://images.unsplash.com/photo-1594223274512-ad4803739b7c?w=500&h=500&fit=crop&auto=format";
                    break;
                case "Nike Air Jordan Cap":
                    newImageUrl = "https://images.unsplash.com/photo-1588850561407-ed78c282e89b?w=500&h=500&fit=crop&auto=format";
                    break;
                    
                default:
                    // Nếu không tìm thấy tên sản phẩm, dùng ảnh mặc định dựa trên category
                    if (product.getCategory() != null) {
                        switch (product.getCategory().getName()) {
                            case "Sneaker":
                                newImageUrl = "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=500&h=500&fit=crop&auto=format";
                                break;
                            case "Trading Card":
                                newImageUrl = "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500&h=500&fit=crop&auto=format";
                                break;
                            case "Figure":
                                newImageUrl = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=500&h=500&fit=crop&auto=format";
                                break;
                            case "Apparel":
                                newImageUrl = "https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=500&h=500&fit=crop&auto=format";
                                break;
                            case "Accessories":
                                newImageUrl = "https://images.unsplash.com/photo-1618221195710-dd6b41faaea8?w=500&h=500&fit=crop&auto=format";
                                break;
                            default:
                                newImageUrl = "https://images.unsplash.com/photo-1560472354-b33ff0c44a43?w=500&h=500&fit=crop&auto=format";
                                break;
                        }
                    } else {
                        newImageUrl = "https://images.unsplash.com/photo-1560472354-b33ff0c44a43?w=500&h=500&fit=crop&auto=format";
                    }
                    break;
            }
            
            // Cập nhật ảnh nếu khác với ảnh hiện tại
            if (newImageUrl != null && !newImageUrl.equals(product.getImageUrl())) {
                product.setImageUrl(newImageUrl);
                productRepo.save(product);
                System.out.println("Updated image for: " + product.getTitle());
            }
        }
        
        System.out.println("Updated images for " + allProducts.size() + " products");
    }
}
