-- Categories data extracted from DataLoader
-- Generated on: 2025-10-15

INSERT INTO category (id, name, slug, description, active, created_at) VALUES 
(1, 'Sneaker', 'sneaker', 'Giày sneaker các thương hiệu', true, CURRENT_TIMESTAMP),
(2, 'Trading Card', 'card', 'Thẻ giao dịch thể thao, game', true, CURRENT_TIMESTAMP),
(3, 'Figure', 'figure', 'Mô hình collectible', true, CURRENT_TIMESTAMP),
(4, 'Apparel', 'apparel', 'Quần áo thể thao', true, CURRENT_TIMESTAMP),
(5, 'Accessories', 'accessories', 'Phụ kiện thể thao', true, CURRENT_TIMESTAMP);

-- Reset sequence for auto-increment
ALTER SEQUENCE category_seq RESTART WITH 6;
