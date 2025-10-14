-- Brands data extracted from DataLoader
-- Generated on: 2025-10-15

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

-- Reset sequence for auto-increment
ALTER SEQUENCE brand_seq RESTART WITH 18;
