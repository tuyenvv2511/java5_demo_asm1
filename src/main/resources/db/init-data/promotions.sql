-- Promotions data extracted from DataLoader
-- Generated on: 2025-10-15
-- Note: Contains both active and inactive promotions for testing

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

-- Reset sequence for auto-increment
ALTER SEQUENCE promotion_seq RESTART WITH 24;
