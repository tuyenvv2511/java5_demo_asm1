-- Products data extracted from DataLoader
-- Generated on: 2025-10-15
-- Note: This file contains sample products for testing

-- Sample Products (main products from DataLoader)
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

-- Reset sequence for auto-increment
ALTER SEQUENCE product_seq RESTART WITH 24;
