-- Users data extracted from DataLoader
-- Generated on: 2025-10-15
-- Note: Passwords are encoded using BCrypt

INSERT INTO users (id, username, email, password, role, active, created_at) VALUES 
(1, 'admin', 'admin@collectx.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ADMIN', true, CURRENT_TIMESTAMP),
(2, 'seller1', 'seller1@collectx.com', '$2a$10$c7fX8l5qK6K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K', 'SELLER', true, CURRENT_TIMESTAMP),
(3, 'buyer1', 'buyer1@collectx.com', '$2a$10$c7fX8l5qK6K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K', 'BUYER', true, CURRENT_TIMESTAMP),
(4, 'collector', 'collector@collectx.com', '$2a$10$c7fX8l5qK6K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K', 'BUYER', true, CURRENT_TIMESTAMP),
(5, 'trader', 'trader@collectx.com', '$2a$10$c7fX8l5qK6K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K5K', 'SELLER', true, CURRENT_TIMESTAMP);

-- Reset sequence for auto-increment
ALTER SEQUENCE users_seq RESTART WITH 6;
