-- Complete data initialization script
-- Generated on: 2025-10-15
-- This script loads all initial data for the CollectX application

-- Disable foreign key checks temporarily
SET REFERENTIAL_INTEGRITY FALSE;

-- Clear existing data (in reverse order of dependencies)
DELETE FROM sale;
DELETE FROM promotion;
DELETE FROM product;
DELETE FROM users;
DELETE FROM category;
DELETE FROM brand;

-- Reset sequences
ALTER SEQUENCE brand_seq RESTART WITH 1;
ALTER SEQUENCE category_seq RESTART WITH 1;
ALTER SEQUENCE users_seq RESTART WITH 1;
ALTER SEQUENCE product_seq RESTART WITH 1;
ALTER SEQUENCE promotion_seq RESTART WITH 1;
ALTER SEQUENCE sale_seq RESTART WITH 1;

-- Enable foreign key checks
SET REFERENTIAL_INTEGRITY TRUE;

-- Load brands
INSERT INTO brand (id, name, country, slug, active, created_at) VALUES 
(1, 'Nike', 'US', 'nike', true, CURRENT_TIMESTAMP),
(2, 'Adidas', 'DE', 'adidas', true, CURRENT_TIMESTAMP),
(3, 'Panini', 'IT', 'panini', true, CURRENT_TIMESTAMP),
(4, 'Jordan', 'US', 'jordan', true, CURRENT_TIMESTAMP),
(5, 'Converse', 'US', 'converse', true, CURRENT_TIMESTAMP),
(6, 'Puma', 'DE', 'puma', true, CURRENT_TIMESTAMP),
(7, 'New Balance', 'US', 'new-balance', true, CURRENT_TIMESTAMP),
(8, 'Vans', 'US', 'vans', true, CURRENT_TIMESTAMP),
(9, 'Supreme', 'US', 'supreme', true, CURRENT_TIMESTAMP),
(10, 'Off-White', 'IT', 'off-white', true, CURRENT_TIMESTAMP),
(11, 'Topps', 'US', 'topps', true, CURRENT_TIMESTAMP),
(12, 'Funko', 'US', 'funko', true, CURRENT_TIMESTAMP),
(13, 'Bandai', 'JP', 'bandai', true, CURRENT_TIMESTAMP),
(14, 'Hasbro', 'US', 'hasbro', true, CURRENT_TIMESTAMP),
(15, 'Lego', 'DK', 'lego', true, CURRENT_TIMESTAMP),
(16, 'Stussy', 'US', 'stussy', true, CURRENT_TIMESTAMP),
(17, 'Champion', 'US', 'champion', true, CURRENT_TIMESTAMP);

-- Load categories
INSERT INTO category (id, name, slug, description, active, created_at) VALUES 
(1, 'Sneaker', 'sneaker', 'Giày sneaker các thương hiệu', true, CURRENT_TIMESTAMP),
(2, 'Trading Card', 'card', 'Thẻ giao dịch thể thao, game', true, CURRENT_TIMESTAMP),
(3, 'Figure', 'figure', 'Mô hình collectible', true, CURRENT_TIMESTAMP),
(4, 'Apparel', 'apparel', 'Quần áo thể thao', true, CURRENT_TIMESTAMP),
(5, 'Accessories', 'accessories', 'Phụ kiện thể thao', true, CURRENT_TIMESTAMP);

-- Load users (with BCrypt encoded passwords)
INSERT INTO users (id, username, email, password, role, active, created_at) VALUES 
(1, 'admin', 'admin@collectx.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ADMIN', true, CURRENT_TIMESTAMP),
(2, 'seller1', 'seller1@collectx.com', '$2a$10$c7fX8l5qK6K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K', 'SELLER', true, CURRENT_TIMESTAMP),
(3, 'buyer1', 'buyer1@collectx.com', '$2a$10$c7fX8l5qK6K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K', 'BUYER', true, CURRENT_TIMESTAMP),
(4, 'collector', 'collector@collectx.com', '$2a$10$c7fX8l5qK6K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K', 'BUYER', true, CURRENT_TIMESTAMP),
(5, 'trader', 'trader@collectx.com', '$2a$10$c7fX8l5qK6K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K', 'SELLER', true, CURRENT_TIMESTAMP);

-- Load products (sample products)
INSERT INTO product (id, title, description, image_url, price, condition_grade, active, brand_id, category_id, seller_id, created_at) VALUES 
(1, 'Nike Dunk Low Panda', 'Size 42, fullbox', '/uploads/images/nike_dunk_low_panda.jpg', 4200000, 'LIKE_NEW', true, 1, 1, 2, CURRENT_TIMESTAMP),
(2, 'Adidas Samba OG', 'Size 40, like new', '/uploads/images/adidas_samba_og.jpg', 3200000, 'LIKE_NEW', true, 2, 1, 2, CURRENT_TIMESTAMP),
(3, 'Panini Prizm Messi #15', 'Pack fresh', '/uploads/images/panini_prizm_messi__15.jpg', 2500000, 'NEW', true, 3, 2, 4, CURRENT_TIMESTAMP),
(4, 'Jordan 1 Retro High', 'Size 41, deadstock', '/uploads/images/jordan_1_retro_high.jpg', 5500000, 'NEW', true, 4, 1, 5, CURRENT_TIMESTAMP),
(5, 'Converse Chuck Taylor', 'Size 39, vintage', '/uploads/images/converse_chuck_taylor.jpg', 1800000, 'GOOD', true, 5, 1, 4, CURRENT_TIMESTAMP),
(6, 'Puma RS-X Reinvention', 'Size 42, limited edition', 'https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500&h=500&fit=crop&auto=format', 2800000, 'LIKE_NEW', true, 6, 1, 5, CURRENT_TIMESTAMP),
(7, 'New Balance 550 White', 'Size 40, fullbox', 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=500&h=500&fit=crop&auto=format', 3200000, 'NEW', true, 7, 1, 2, CURRENT_TIMESTAMP),
(8, 'Vans Old Skool Black', 'Size 39, vintage', 'https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500&h=500&fit=crop&auto=format', 1200000, 'GOOD', true, 8, 1, 4, CURRENT_TIMESTAMP),
(9, 'Nike Air Max 90', 'Size 41, infrared', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500&h=500&fit=crop&auto=format', 3500000, 'LIKE_NEW', true, 1, 1, 5, CURRENT_TIMESTAMP),
(10, 'Adidas Ultraboost 22', 'Size 42, white', 'https://images.unsplash.com/photo-1560769629-975ec94e6a86?w=500&h=500&fit=crop&auto=format', 4000000, 'NEW', true, 2, 1, 2, CURRENT_TIMESTAMP),
(11, 'Topps Chrome Kobe Bryant Rookie', 'PSA 9, 1996', 'https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500&h=500&fit=crop&auto=format', 15000000, 'NEW', true, 11, 2, 5, CURRENT_TIMESTAMP),
(12, 'Panini Prizm Luka Doncic RC', 'Silver prizm, 2018', 'https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=500&h=500&fit=crop&auto=format', 8500000, 'NEW', true, 3, 2, 4, CURRENT_TIMESTAMP),
(13, 'Topps Chrome Tom Brady Rookie', 'BGS 9.5, 2000', 'https://images.unsplash.com/photo-1546519638-68e109498ffc?w=500&h=500&fit=crop&auto=format', 12000000, 'NEW', true, 11, 2, 5, CURRENT_TIMESTAMP),
(14, 'Funko Pop Batman #01', 'Vaulted, mint condition', '/uploads/images/funko_pop_batman__01.jpg', 800000, 'NEW', true, 12, 3, 4, CURRENT_TIMESTAMP),
(15, 'Bandai Gundam RX-78-2', 'Master Grade, unbuilt', '/uploads/images/bandai_gundam_rx_78_2.jpg', 1200000, 'NEW', true, 13, 3, 2, CURRENT_TIMESTAMP),
(16, 'Hasbro Transformers Optimus Prime', 'Vintage 1984, complete', '/uploads/images/hasbro_transformers_optimus_prime.jpg', 2500000, 'GOOD', true, 14, 3, 5, CURRENT_TIMESTAMP),
(17, 'Lego Star Wars Death Star', 'Set 10188, complete with box', '/uploads/images/lego_star_wars_death_star.jpg', 18000000, 'NEW', true, 15, 3, 4, CURRENT_TIMESTAMP),
(18, 'Supreme Box Logo Hoodie', 'FW20, size L, black', 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=500&h=500&fit=crop&auto=format', 8500000, 'LIKE_NEW', true, 9, 4, 5, CURRENT_TIMESTAMP),
(19, 'Off-White Industrial Belt', 'Yellow, 200cm', 'https://images.unsplash.com/photo-1594223274512-ad4803739b7c?w=500&h=500&fit=crop&auto=format', 6500000, 'LIKE_NEW', true, 10, 4, 2, CURRENT_TIMESTAMP),
(20, 'Stussy World Tour Tee', 'Size M, white, vintage', 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500&h=500&fit=crop&auto=format', 1800000, 'GOOD', true, 16, 4, 4, CURRENT_TIMESTAMP),
(21, 'Champion Reverse Weave Hoodie', 'Size XL, grey', 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=500&h=500&fit=crop&auto=format', 2200000, 'LIKE_NEW', true, 17, 4, 2, CURRENT_TIMESTAMP),
(22, 'Supreme TNF Mountain Tee', 'FW18, size S', 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500&h=500&fit=crop&auto=format', 3500000, 'LIKE_NEW', true, 9, 5, 5, CURRENT_TIMESTAMP),
(23, 'Nike Air Jordan Cap', 'Snapback, black/red', 'https://images.unsplash.com/photo-1588850561407-ed78c282e89b?w=500&h=500&fit=crop&auto=format', 800000, 'NEW', true, 4, 5, 2, CURRENT_TIMESTAMP);

-- Load promotions (active, expired, and upcoming)
INSERT INTO promotion (id, product_id, type, discount_value, start_date, end_date, description, active, created_at) VALUES 
-- Active Promotions
(1, 1, 'PERCENTAGE', 15.00, DATEADD(DAY, -1, CURRENT_TIMESTAMP), DATEADD(DAY, 6, CURRENT_TIMESTAMP), 'Khuyến mãi Black Friday - Giảm 15% cho Nike Dunk Low Panda', true, CURRENT_TIMESTAMP),
(2, 2, 'FIXED_AMOUNT', 500000.00, DATEADD(DAY, -2, CURRENT_TIMESTAMP), DATEADD(DAY, 8, CURRENT_TIMESTAMP), 'Flash Sale - Giảm ngay 500,000 VNĐ cho Adidas Samba OG', true, CURRENT_TIMESTAMP),
(3, 3, 'PERCENTAGE', 20.00, CURRENT_TIMESTAMP, DATEADD(DAY, 5, CURRENT_TIMESTAMP), 'Weekend Special - Giảm 20% cho thẻ Panini Prizm Messi', true, CURRENT_TIMESTAMP),
(4, 4, 'FIXED_AMOUNT', 1000000.00, DATEADD(DAY, 1, CURRENT_TIMESTAMP), DATEADD(DAY, 15, CURRENT_TIMESTAMP), 'Premium Sale - Giảm 1,000,000 VNĐ cho Jordan 1 Retro High', true, CURRENT_TIMESTAMP),
(5, 5, 'PERCENTAGE', 25.00, DATEADD(DAY, 2, CURRENT_TIMESTAMP), DATEADD(DAY, 5, CURRENT_TIMESTAMP), 'Limited Time Offer - Giảm 25% cho Converse Chuck Taylor vintage', true, CURRENT_TIMESTAMP),
(6, 6, 'PERCENTAGE', 12.00, DATEADD(DAY, -1, CURRENT_TIMESTAMP), DATEADD(DAY, 4, CURRENT_TIMESTAMP), '🔥 Hot Deal - Giảm 12% cho Puma RS-X Reinvention', true, CURRENT_TIMESTAMP),
(7, 7, 'FIXED_AMOUNT', 300000.00, DATEADD(DAY, -2, CURRENT_TIMESTAMP), DATEADD(DAY, 6, CURRENT_TIMESTAMP), '💥 Flash Sale - Giảm ngay 300K cho New Balance 550', true, CURRENT_TIMESTAMP),
(8, 8, 'PERCENTAGE', 8.00, DATEADD(DAY, -3, CURRENT_TIMESTAMP), DATEADD(DAY, 7, CURRENT_TIMESTAMP), '⭐ Weekend Special - Giảm 8% cho Vans Old Skool', true, CURRENT_TIMESTAMP),
(9, 9, 'FIXED_AMOUNT', 250000.00, DATEADD(DAY, -1, CURRENT_TIMESTAMP), DATEADD(DAY, 9, CURRENT_TIMESTAMP), '🚀 Limited Time - Giảm 250K cho Nike Air Max 90', true, CURRENT_TIMESTAMP),
(10, 10, 'PERCENTAGE', 18.00, DATEADD(DAY, -4, CURRENT_TIMESTAMP), DATEADD(DAY, 3, CURRENT_TIMESTAMP), '🎯 Mega Sale - Giảm 18% cho Adidas Ultraboost 22', true, CURRENT_TIMESTAMP),
(11, 11, 'FIXED_AMOUNT', 1000000.00, DATEADD(DAY, -2, CURRENT_TIMESTAMP), DATEADD(DAY, 5, CURRENT_TIMESTAMP), '🏆 Legendary Card - Giảm 1M cho Kobe Bryant Rookie', true, CURRENT_TIMESTAMP),
(12, 12, 'PERCENTAGE', 15.00, DATEADD(DAY, -1, CURRENT_TIMESTAMP), DATEADD(DAY, 6, CURRENT_TIMESTAMP), '⭐ Rising Star - Giảm 15% cho Luka Doncic RC', true, CURRENT_TIMESTAMP),
(13, 14, 'FIXED_AMOUNT', 50000.00, DATEADD(DAY, -3, CURRENT_TIMESTAMP), DATEADD(DAY, 4, CURRENT_TIMESTAMP), '🦇 Hero Collection - Giảm 50K cho Batman Funko Pop', true, CURRENT_TIMESTAMP),
(14, 15, 'PERCENTAGE', 10.00, DATEADD(DAY, -2, CURRENT_TIMESTAMP), DATEADD(DAY, 8, CURRENT_TIMESTAMP), '🤖 Anime Collection - Giảm 10% cho Gundam RX-78-2', true, CURRENT_TIMESTAMP),
(15, 18, 'FIXED_AMOUNT', 800000.00, DATEADD(DAY, -1, CURRENT_TIMESTAMP), DATEADD(DAY, 7, CURRENT_TIMESTAMP), '🔥 Supreme Drop - Giảm 800K cho Box Logo Hoodie', true, CURRENT_TIMESTAMP),
(16, 19, 'PERCENTAGE', 12.00, DATEADD(DAY, -4, CURRENT_TIMESTAMP), DATEADD(DAY, 3, CURRENT_TIMESTAMP), '⚡ Streetwear Sale - Giảm 12% cho Off-White Belt', true, CURRENT_TIMESTAMP),

-- Expired Promotions
(17, 6, 'PERCENTAGE', 20.00, DATEADD(DAY, -20, CURRENT_TIMESTAMP), DATEADD(DAY, -15, CURRENT_TIMESTAMP), '🎉 Grand Opening Sale - Giảm 20% cho Puma RS-X (Đã kết thúc)', false, DATEADD(DAY, -20, CURRENT_TIMESTAMP)),
(18, 7, 'FIXED_AMOUNT', 400000.00, DATEADD(DAY, -25, CURRENT_TIMESTAMP), DATEADD(DAY, -18, CURRENT_TIMESTAMP), '🏃‍♂️ Running Week - Giảm 400K cho New Balance (Đã kết thúc)', false, DATEADD(DAY, -25, CURRENT_TIMESTAMP)),
(19, 11, 'PERCENTAGE', 25.00, DATEADD(DAY, -30, CURRENT_TIMESTAMP), DATEADD(DAY, -22, CURRENT_TIMESTAMP), '🏀 Legend Tribute - Giảm 25% cho Kobe Card (Đã kết thúc)', false, DATEADD(DAY, -30, CURRENT_TIMESTAMP)),
(20, 18, 'PERCENTAGE', 30.00, DATEADD(DAY, -35, CURRENT_TIMESTAMP), DATEADD(DAY, -28, CURRENT_TIMESTAMP), '🔥 Black Friday - Giảm 30% cho Supreme Hoodie (Đã kết thúc)', false, DATEADD(DAY, -35, CURRENT_TIMESTAMP)),
(21, 15, 'FIXED_AMOUNT', 150000.00, DATEADD(DAY, -28, CURRENT_TIMESTAMP), DATEADD(DAY, -21, CURRENT_TIMESTAMP), '🤖 Gundam Anniversary - Giảm 150K cho RX-78-2 (Đã kết thúc)', false, DATEADD(DAY, -28, CURRENT_TIMESTAMP)),

-- Upcoming Promotions
(22, 8, 'PERCENTAGE', 15.00, DATEADD(DAY, 2, CURRENT_TIMESTAMP), DATEADD(DAY, 12, CURRENT_TIMESTAMP), '🎨 Art Week - Giảm 15% cho Vans (Sắp bắt đầu)', false, CURRENT_TIMESTAMP),
(23, 12, 'FIXED_AMOUNT', 500000.00, DATEADD(DAY, 5, CURRENT_TIMESTAMP), DATEADD(DAY, 15, CURRENT_TIMESTAMP), '🏀 Playoff Season - Giảm 500K cho Luka Card (Sắp bắt đầu)', false, CURRENT_TIMESTAMP);

-- Load sales (historical data for AI analysis)
INSERT INTO sale (id, product_id, buyer_id, seller_id, sold_price, sold_at) VALUES 
-- Recent Sales
(1, 1, 3, 2, 4000000, CURRENT_TIMESTAMP),
(2, 2, 4, 2, 3000000, CURRENT_TIMESTAMP),
(3, 3, 5, 4, 2400000, CURRENT_TIMESTAMP),
(4, 5, 3, 4, 1700000, CURRENT_TIMESTAMP),

-- Historical sales data (last 30-60 days)
(5, 1, 3, 2, 3200000, DATEADD(DAY, -5, CURRENT_TIMESTAMP)),
(6, 1, 4, 2, 3100000, DATEADD(DAY, -10, CURRENT_TIMESTAMP)),
(7, 1, 5, 2, 3300000, DATEADD(DAY, -15, CURRENT_TIMESTAMP)),
(8, 1, 3, 2, 3150000, DATEADD(DAY, -20, CURRENT_TIMESTAMP)),
(9, 1, 4, 2, 3250000, DATEADD(DAY, -25, CURRENT_TIMESTAMP)),
(10, 2, 5, 2, 2800000, DATEADD(DAY, -3, CURRENT_TIMESTAMP)),
(11, 2, 3, 2, 2750000, DATEADD(DAY, -8, CURRENT_TIMESTAMP)),
(12, 2, 4, 2, 2850000, DATEADD(DAY, -12, CURRENT_TIMESTAMP)),
(13, 2, 5, 2, 2700000, DATEADD(DAY, -18, CURRENT_TIMESTAMP)),
(14, 2, 3, 2, 2900000, DATEADD(DAY, -22, CURRENT_TIMESTAMP)),
(15, 4, 4, 5, 4500000, DATEADD(DAY, -4, CURRENT_TIMESTAMP)),
(16, 4, 5, 5, 4600000, DATEADD(DAY, -9, CURRENT_TIMESTAMP)),
(17, 4, 3, 5, 4400000, DATEADD(DAY, -14, CURRENT_TIMESTAMP)),
(18, 4, 4, 5, 4700000, DATEADD(DAY, -19, CURRENT_TIMESTAMP)),
(19, 4, 5, 5, 4550000, DATEADD(DAY, -24, CURRENT_TIMESTAMP)),
(20, 5, 3, 4, 1600000, DATEADD(DAY, -6, CURRENT_TIMESTAMP)),
(21, 5, 4, 4, 1650000, DATEADD(DAY, -11, CURRENT_TIMESTAMP)),
(22, 5, 5, 4, 1550000, DATEADD(DAY, -16, CURRENT_TIMESTAMP)),
(23, 5, 3, 4, 1680000, DATEADD(DAY, -21, CURRENT_TIMESTAMP)),
(24, 5, 4, 4, 1620000, DATEADD(DAY, -26, CURRENT_TIMESTAMP)),
(25, 3, 5, 4, 2500000, DATEADD(DAY, -2, CURRENT_TIMESTAMP)),
(26, 3, 4, 4, 2450000, DATEADD(DAY, -7, CURRENT_TIMESTAMP)),
(27, 3, 3, 4, 2550000, DATEADD(DAY, -13, CURRENT_TIMESTAMP)),
(28, 3, 5, 4, 2350000, DATEADD(DAY, -17, CURRENT_TIMESTAMP)),
(29, 3, 4, 4, 2600000, DATEADD(DAY, -23, CURRENT_TIMESTAMP)),

-- Additional product sales
(30, 6, 3, 5, 2700000, DATEADD(DAY, -7, CURRENT_TIMESTAMP)),
(31, 6, 5, 5, 2850000, DATEADD(DAY, -14, CURRENT_TIMESTAMP)),
(32, 7, 4, 2, 3100000, DATEADD(DAY, -6, CURRENT_TIMESTAMP)),
(33, 7, 3, 2, 3250000, DATEADD(DAY, -13, CURRENT_TIMESTAMP)),
(34, 8, 5, 4, 1150000, DATEADD(DAY, -9, CURRENT_TIMESTAMP)),
(35, 8, 4, 4, 1250000, DATEADD(DAY, -16, CURRENT_TIMESTAMP)),
(36, 9, 3, 5, 3400000, DATEADD(DAY, -5, CURRENT_TIMESTAMP)),
(37, 9, 5, 5, 3600000, DATEADD(DAY, -11, CURRENT_TIMESTAMP)),
(38, 10, 4, 2, 3900000, DATEADD(DAY, -4, CURRENT_TIMESTAMP)),
(39, 10, 3, 2, 4100000, DATEADD(DAY, -10, CURRENT_TIMESTAMP)),
(40, 11, 5, 5, 14800000, DATEADD(DAY, -8, CURRENT_TIMESTAMP)),
(41, 11, 4, 5, 15200000, DATEADD(DAY, -15, CURRENT_TIMESTAMP)),
(42, 12, 3, 4, 8300000, DATEADD(DAY, -6, CURRENT_TIMESTAMP)),
(43, 12, 5, 4, 8700000, DATEADD(DAY, -12, CURRENT_TIMESTAMP)),
(44, 14, 4, 4, 780000, DATEADD(DAY, -3, CURRENT_TIMESTAMP)),
(45, 14, 3, 4, 820000, DATEADD(DAY, -9, CURRENT_TIMESTAMP)),
(46, 15, 5, 2, 1150000, DATEADD(DAY, -7, CURRENT_TIMESTAMP)),
(47, 15, 4, 2, 1250000, DATEADD(DAY, -14, CURRENT_TIMESTAMP)),
(48, 18, 5, 5, 8300000, DATEADD(DAY, -5, CURRENT_TIMESTAMP)),
(49, 18, 3, 5, 8700000, DATEADD(DAY, -11, CURRENT_TIMESTAMP)),
(50, 19, 4, 2, 6300000, DATEADD(DAY, -8, CURRENT_TIMESTAMP)),
(51, 19, 5, 2, 6700000, DATEADD(DAY, -16, CURRENT_TIMESTAMP)),

-- Older sales (30-60 days ago)
(52, 1, 3, 2, 3000000, DATEADD(DAY, -35, CURRENT_TIMESTAMP)),
(53, 2, 5, 2, 2600000, DATEADD(DAY, -40, CURRENT_TIMESTAMP)),
(54, 4, 4, 5, 4200000, DATEADD(DAY, -45, CURRENT_TIMESTAMP));

-- Update sequences to continue from current max IDs
ALTER SEQUENCE brand_seq RESTART WITH 18;
ALTER SEQUENCE category_seq RESTART WITH 6;
ALTER SEQUENCE users_seq RESTART WITH 6;
ALTER SEQUENCE product_seq RESTART WITH 24;
ALTER SEQUENCE promotion_seq RESTART WITH 24;
ALTER SEQUENCE sale_seq RESTART WITH 55;

-- Commit transaction
COMMIT;

-- Display summary
SELECT 'Data initialization completed successfully!' as message;
SELECT 'Brands: ' || COUNT(*) as summary FROM brand;
SELECT 'Categories: ' || COUNT(*) as summary FROM category;
SELECT 'Users: ' || COUNT(*) as summary FROM users;
SELECT 'Products: ' || COUNT(*) as summary FROM product;
SELECT 'Promotions: ' || COUNT(*) as summary FROM promotion;
SELECT 'Sales: ' || COUNT(*) as summary FROM sale;
