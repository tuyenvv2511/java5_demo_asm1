# Hướng dẫn chạy Project

## 🚀 Cách chạy ứng dụng

### 1. Yêu cầu hệ thống
- Java 17 hoặc cao hơn
- Maven (hoặc sử dụng Maven wrapper có sẵn)

### 2. Chạy ứng dụng

#### Cách 1: Sử dụng Maven Wrapper (Khuyến nghị)
```bash
# Di chuyển vào thư mục project
cd C:\Users\tuyen\IdeaProjects\demo_j5_asm1

# Chạy ứng dụng
.\mvnw.cmd spring-boot:run
```

#### Cách 2: Sử dụng Maven (nếu đã cài đặt)
```bash
mvn spring-boot:run
```

### 3. Truy cập ứng dụng
- **URL chính**: http://localhost:8080
- **Trang đơn hàng**: http://localhost:8080/orders
- **Sản phẩm**: http://localhost:8080/products
- **Thương hiệu**: http://localhost:8080/brands
- **Danh mục**: http://localhost:8080/categories

## 🔧 Xử lý lỗi thường gặp

### Lỗi Database bị lock
```
Database may be already in use: "C:/Users/tuyen/IdeaProjects/demo_j5_asm1/data/collectx.mv.db"
```

**Giải pháp:**
1. Dừng tất cả process Java:
   ```bash
   taskkill /f /im java.exe
   ```

2. Xóa file database cũ:
   ```bash
   del data\collectx.mv.db
   del data\collectx.trace.db
   ```

3. Chạy lại ứng dụng:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

### Lỗi Port đã được sử dụng
Nếu port 8080 đã được sử dụng, có thể thay đổi trong file `application.properties`:
```properties
server.port=8081
```

## 📋 Tính năng đã triển khai

### ✅ Hệ thống phân quyền đơn hàng
- **Người bán (SELLER)**: Chỉ có thể chuyển đổi trạng thái theo thứ tự
  - `PENDING` → `CONFIRMED` → `SHIPPED` → `DELIVERED`
  - Có thể hủy đơn hàng khi trạng thái là `PENDING` hoặc `CONFIRMED`

- **Người mua (BUYER)**: Chỉ có thể hoàn tất đơn hàng
  - `DELIVERED` → `COMPLETED`
  - Có thể hủy đơn hàng khi trạng thái là `PENDING` hoặc `CONFIRMED`

### ✅ Các tính năng khác
- Quản lý sản phẩm
- Quản lý thương hiệu và danh mục
- Hệ thống khuyến mãi
- Upload hình ảnh
- Đa ngôn ngữ (Tiếng Việt, Tiếng Anh, Tiếng Nhật)

## 🧪 Test hệ thống

### Chạy test
```bash
.\mvnw.cmd test
```

### Test phân quyền đơn hàng
Các test case đã được tạo trong `OrderServiceTest.java`:
- Test người bán có thể chuyển PENDING → CONFIRMED
- Test người bán không thể bỏ qua bước
- Test người mua có thể hoàn tất đơn hàng DELIVERED
- Test người mua không thể hoàn tất đơn hàng chưa DELIVERED

## 📁 Cấu trúc project

```
src/
├── main/
│   ├── java/org/example/demo_j5_asm1/
│   │   ├── controller/     # Các controller xử lý request
│   │   ├── entity/         # Các entity JPA
│   │   ├── repository/     # Các repository interface
│   │   ├── service/        # Business logic
│   │   └── config/         # Cấu hình
│   └── resources/
│       ├── templates/      # Thymeleaf templates
│       ├── static/         # CSS, JS, images
│       └── i18n/          # File đa ngôn ngữ
└── test/                   # Test cases
```

## 🎯 Các endpoint chính

| Endpoint | Mô tả | Quyền hạn |
|----------|-------|-----------|
| `/` | Trang chủ | Tất cả |
| `/products` | Danh sách sản phẩm | Tất cả |
| `/orders` | Quản lý đơn hàng | Tất cả |
| `/orders/buy/{id}` | Mua sản phẩm | BUYER |
| `/orders/{id}/status` | Cập nhật trạng thái | SELLER |
| `/orders/{id}/complete` | Hoàn tất đơn hàng | BUYER |
| `/orders/{id}/cancel` | Hủy đơn hàng | BUYER/SELLER |

## 🔐 Bảo mật

- Kiểm tra quyền sở hữu đơn hàng
- Kiểm tra role của user
- Validate chuyển đổi trạng thái tuần tự
- Exception handling với thông báo lỗi rõ ràng

## 📞 Hỗ trợ

Nếu gặp vấn đề, hãy kiểm tra:
1. Java version (cần Java 17+)
2. Port 8080 có bị chiếm dụng không
3. File database có bị lock không
4. Log của ứng dụng để xem lỗi chi tiết
