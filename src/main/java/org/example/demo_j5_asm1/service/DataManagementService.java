package org.example.demo_j5_asm1.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service để quản lý dữ liệu database
 * Cho phép load/unload dữ liệu từ SQL files
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DataManagementService {
    
    private final JdbcTemplate jdbcTemplate;
    
    /**
     * Load dữ liệu từ file SQL
     * @param sqlFileName tên file SQL (ví dụ: "init-all-data.sql")
     * @return số lượng statements đã thực thi
     */
    @Transactional
    public int loadDataFromSql(String sqlFileName) {
        try {
            Resource resource = new ClassPathResource("db/init-data/" + sqlFileName);
            
            if (!resource.exists()) {
                log.error("SQL file not found: {}", sqlFileName);
                throw new RuntimeException("SQL file not found: " + sqlFileName);
            }
            
            log.info("Loading data from SQL file: {}", sqlFileName);
            
            // Đọc file SQL
            List<String> statements = readSqlStatements(resource);
            
            // Thực thi từng statement
            int executedCount = 0;
            for (String statement : statements) {
                if (statement.trim().isEmpty() || statement.trim().startsWith("--")) {
                    continue; // Bỏ qua comment và dòng trống
                }
                
                try {
                    jdbcTemplate.execute(statement);
                    executedCount++;
                    
                    // Log progress cho statements lớn
                    if (statement.toUpperCase().contains("INSERT") && executedCount % 10 == 0) {
                        log.info("Executed {} statements...", executedCount);
                    }
                } catch (Exception e) {
                    log.error("Error executing statement: {}", statement, e);
                    throw new RuntimeException("Error executing SQL statement: " + e.getMessage(), e);
                }
            }
            
            log.info("Successfully loaded data from {}: {} statements executed", sqlFileName, executedCount);
            return executedCount;
            
        } catch (IOException e) {
            log.error("Error reading SQL file: {}", sqlFileName, e);
            throw new RuntimeException("Error reading SQL file: " + e.getMessage(), e);
        }
    }
    
    /**
     * Load tất cả dữ liệu mặc định
     * @return kết quả load data
     */
    @Transactional
    public DataLoadResult loadAllDefaultData() {
        log.info("Starting to load all default data...");
        
        try {
            // Backup current data count
            DataCount before = getDataCount();
            log.info("Data count before load: {}", before);
            
            // Load data
            int statementsExecuted = loadDataFromSql("init-all-data.sql");
            
            // Check data count after
            DataCount after = getDataCount();
            log.info("Data count after load: {}", after);
            
            return DataLoadResult.builder()
                    .success(true)
                    .statementsExecuted(statementsExecuted)
                    .beforeCount(before)
                    .afterCount(after)
                    .message("Successfully loaded all default data")
                    .build();
                    
        } catch (Exception e) {
            log.error("Error loading default data", e);
            return DataLoadResult.builder()
                    .success(false)
                    .statementsExecuted(0)
                    .errorMessage(e.getMessage())
                    .message("Failed to load default data: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * Load dữ liệu từng loại riêng biệt
     * @param dataType loại dữ liệu (brands, categories, users, products, promotions, sales)
     * @return kết quả load data
     */
    @Transactional
    public DataLoadResult loadSpecificData(String dataType) {
        String sqlFileName = dataType + ".sql";
        log.info("Loading specific data: {}", dataType);
        
        try {
            DataCount before = getDataCount();
            int statementsExecuted = loadDataFromSql(sqlFileName);
            DataCount after = getDataCount();
            
            return DataLoadResult.builder()
                    .success(true)
                    .statementsExecuted(statementsExecuted)
                    .beforeCount(before)
                    .afterCount(after)
                    .message("Successfully loaded " + dataType + " data")
                    .build();
                    
        } catch (Exception e) {
            log.error("Error loading {} data", dataType, e);
            return DataLoadResult.builder()
                    .success(false)
                    .statementsExecuted(0)
                    .errorMessage(e.getMessage())
                    .message("Failed to load " + dataType + " data: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * Kiểm tra trạng thái dữ liệu hiện tại
     * @return thống kê dữ liệu
     */
    public DataCount getDataCount() {
        try {
            Integer brandsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM brand", Integer.class);
            Integer categoriesCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM category", Integer.class);
            Integer usersCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            Integer productsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM product", Integer.class);
            Integer promotionsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM promotion", Integer.class);
            Integer salesCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sale", Integer.class);
            
            DataCount count = DataCount.builder()
                    .brands(brandsCount != null ? brandsCount : 0)
                    .categories(categoriesCount != null ? categoriesCount : 0)
                    .users(usersCount != null ? usersCount : 0)
                    .products(productsCount != null ? productsCount : 0)
                    .promotions(promotionsCount != null ? promotionsCount : 0)
                    .sales(salesCount != null ? salesCount : 0)
                    .build();
                    
            return count;
        } catch (Exception e) {
            log.error("Error getting data count", e);
            return DataCount.builder()
                    .brands(0).categories(0).users(0)
                    .products(0).promotions(0).sales(0)
                    .build();
        }
    }
    
    /**
     * Xóa tất cả dữ liệu (cẩn thận!)
     * @return kết quả xóa dữ liệu
     */
    @Transactional
    public DataLoadResult clearAllData() {
        log.warn("Clearing all data from database!");
        
        try {
            DataCount before = getDataCount();
            
            // Xóa theo thứ tự dependencies
            jdbcTemplate.execute("DELETE FROM sale");
            jdbcTemplate.execute("DELETE FROM promotion");
            jdbcTemplate.execute("DELETE FROM product");
            jdbcTemplate.execute("DELETE FROM users");
            jdbcTemplate.execute("DELETE FROM category");
            jdbcTemplate.execute("DELETE FROM brand");
            
            // Reset sequences
            jdbcTemplate.execute("ALTER SEQUENCE brand_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE category_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE users_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE product_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE promotion_seq RESTART WITH 1");
            jdbcTemplate.execute("ALTER SEQUENCE sale_seq RESTART WITH 1");
            
            DataCount after = getDataCount();
            
            return DataLoadResult.builder()
                    .success(true)
                    .statementsExecuted(6)
                    .beforeCount(before)
                    .afterCount(after)
                    .message("Successfully cleared all data")
                    .build();
                    
        } catch (Exception e) {
            log.error("Error clearing data", e);
            return DataLoadResult.builder()
                    .success(false)
                    .statementsExecuted(0)
                    .errorMessage(e.getMessage())
                    .message("Failed to clear data: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * Đọc các SQL statements từ file
     */
    private List<String> readSqlStatements(Resource resource) throws IOException {
        List<String> statements = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            
            StringBuilder currentStatement = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Bỏ qua comment và dòng trống
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                
                currentStatement.append(line).append(" ");
                
                // Kết thúc statement khi gặp dấu ;
                if (line.endsWith(";")) {
                    String statement = currentStatement.toString().trim();
                    if (!statement.isEmpty()) {
                        statements.add(statement);
                    }
                    currentStatement.setLength(0);
                }
            }
            
            // Thêm statement cuối cùng nếu không kết thúc bằng ;
            if (currentStatement.length() > 0) {
                statements.add(currentStatement.toString().trim());
            }
        }
        
        return statements;
    }
    
    /**
     * Data class để lưu kết quả load data
     */
    @lombok.Data
    @lombok.Builder
    public static class DataLoadResult {
        private boolean success;
        private int statementsExecuted;
        private DataCount beforeCount;
        private DataCount afterCount;
        private String message;
        private String errorMessage;
    }
    
    /**
     * Data class để lưu thống kê dữ liệu
     */
    @lombok.Data
    @lombok.Builder
    public static class DataCount {
        private int brands;
        private int categories;
        private int users;
        private int products;
        private int promotions;
        private int sales;
        
        @Override
        public String toString() {
            return String.format("Brands: %d, Categories: %d, Users: %d, Products: %d, Promotions: %d, Sales: %d",
                    brands, categories, users, products, promotions, sales);
        }
    }
}
