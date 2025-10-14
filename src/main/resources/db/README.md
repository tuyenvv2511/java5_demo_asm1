# Database Management System

## 📁 Cấu trúc thư mục

```
src/main/resources/db/
├── migration/          # Flyway migration scripts (tương lai)
│   ├── V1__Create_tables.sql
│   ├── V2__Insert_brands.sql
│   └── ...
├── init-data/          # Standalone data files
│   ├── init-all-data.sql    # Tất cả dữ liệu
│   ├── brands.sql           # Thương hiệu
│   ├── categories.sql       # Danh mục
│   ├── users.sql            # Người dùng
│   ├── products.sql         # Sản phẩm
│   ├── promotions.sql       # Khuyến mãi
│   └── sales.sql            # Giao dịch
└── README.md           # Hướng dẫn này
```

## 🎯 Mục đích

Hệ thống này cho phép:
- ✅ Tách dữ liệu cứng ra khỏi code Java
- ✅ Quản lý dữ liệu qua SQL files
- ✅ Load/unload dữ liệu khi cần thiết
- ✅ Backup và restore dữ liệu
- ✅ Version control cho dữ liệu

## 🔧 Cách sử dụng

### 1. Qua Admin Panel (Recommended)
- Truy cập: `http://localhost:8080/admin/data`
- Đăng nhập với tài khoản ADMIN
- Sử dụng giao diện web để quản lý dữ liệu

### 2. Qua H2 Console
- Truy cập: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/collectx`
- Username: `sa`
- Password: (để trống)
- Copy/paste SQL từ các file trong `init-data/`

### 3. Qua DataManagementService (Programmatic)
```java
@Autowired
private DataManagementService dataManagementService;

// Load tất cả dữ liệu
dataManagementService.loadAllDefaultData();

// Load dữ liệu cụ thể
dataManagementService.loadSpecificData("brands");

// Xem thống kê
DataCount count = dataManagementService.getDataCount();
```

## 📊 Nội dung dữ liệu

### Brands (17 thương hiệu)
- Nike, Adidas, Panini, Jordan, Converse
- Puma, New Balance, Vans, Supreme, Off-White
- Topps, Funko, Bandai, Hasbro, Lego, Stussy, Champion

### Categories (5 danh mục)
- Sneaker, Trading Card, Figure, Apparel, Accessories

### Users (5 người dùng)
- admin (ADMIN) - admin@collectx.com
- seller1 (SELLER) - seller1@collectx.com
- buyer1 (BUYER) - buyer1@collectx.com
- collector (BUYER) - collector@collectx.com
- trader (SELLER) - trader@collectx.com

### Products (23 sản phẩm mẫu)
- Sneakers: Nike Dunk Low, Adidas Samba, Jordan 1, etc.
- Trading Cards: Kobe Bryant Rookie, Luka Doncic RC, etc.
- Figures: Batman Funko Pop, Gundam RX-78-2, etc.
- Apparel: Supreme Hoodie, Off-White Belt, etc.

### Promotions (23 khuyến mãi)
- 16 active promotions
- 5 expired promotions  
- 2 upcoming promotions

### Sales (54 giao dịch lịch sử)
- Dữ liệu cho AI analysis
- 30 ngày gần nhất
- Đa dạng giá cả và thời gian

## 🔒 Bảo mật

- ✅ Chỉ ADMIN mới có quyền truy cập `/admin/data`
- ✅ Có xác nhận trước khi xóa dữ liệu
- ✅ Backup tự động trước khi thao tác
- ✅ Transaction rollback nếu có lỗi

## ⚠️ Lưu ý quan trọng

1. **Backup dữ liệu**: Luôn backup trước khi load/xóa dữ liệu
2. **Test environment**: Test trên môi trường dev trước khi production
3. **Dependencies**: Thứ tự load: brands → categories → users → products → promotions → sales
4. **Sequences**: Hệ thống tự động reset sequences sau khi load
5. **Foreign keys**: Có disable/enable foreign key checks để tránh lỗi

## 🚀 Production Deployment

### PostgreSQL
```bash
# Connect to production database
psql -h your-host -U your-user -d your-database

# Load data
\i src/main/resources/db/init-data/init-all-data.sql
```

### Docker
```bash
# Copy SQL file to container
docker cp src/main/resources/db/init-data/init-all-data.sql container_name:/tmp/

# Execute in container
docker exec -i container_name psql -U user -d database < /tmp/init-all-data.sql
```

## 📝 Maintenance

### Thêm dữ liệu mới
1. Tạo file SQL mới trong `init-data/`
2. Update `init-all-data.sql`
3. Test trên dev environment
4. Deploy lên production

### Update dữ liệu hiện tại
1. Export dữ liệu từ database
2. Chỉnh sửa SQL files
3. Test migration
4. Deploy

### Troubleshooting
- **Lỗi foreign key**: Kiểm tra thứ tự load dữ liệu
- **Lỗi sequence**: Reset sequences sau khi load
- **Lỗi encoding**: Đảm bảo UTF-8 encoding
- **Lỗi permission**: Kiểm tra quyền truy cập database

## 📞 Support

Nếu gặp vấn đề, hãy:
1. Kiểm tra logs trong console
2. Verify database connection
3. Check file permissions
4. Contact development team
