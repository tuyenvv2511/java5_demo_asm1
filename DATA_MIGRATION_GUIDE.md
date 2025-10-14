# 🗄️ Hướng dẫn Migration Dữ liệu

## ✅ Đã hoàn thành

### 1. **Cấu trúc SQL Files**
```
src/main/resources/db/
├── init-data/
│   ├── init-all-data.sql    ✅ Tất cả dữ liệu
│   ├── brands.sql           ✅ 17 thương hiệu
│   ├── categories.sql       ✅ 5 danh mục
│   ├── users.sql            ✅ 5 người dùng
│   ├── products.sql         ✅ 23 sản phẩm
│   ├── promotions.sql       ✅ 23 khuyến mãi
│   └── sales.sql            ✅ 54 giao dịch
└── README.md               ✅ Hướng dẫn chi tiết
```

### 2. **DataManagementService**
- ✅ Load dữ liệu từ SQL files
- ✅ Quản lý transactions
- ✅ Error handling và rollback
- ✅ Thống kê dữ liệu
- ✅ Clear all data (cẩn thận!)

### 3. **AdminDataController**
- ✅ Web interface cho quản lý dữ liệu
- ✅ API endpoints cho AJAX calls
- ✅ Security (chỉ ADMIN)
- ✅ Real-time statistics

### 4. **Admin Panel UI**
- ✅ Giao diện đẹp và responsive
- ✅ Thống kê real-time
- ✅ Loading states và error handling
- ✅ Confirmation dialogs

## 🎯 Cách sử dụng

### **Phương pháp 1: Admin Panel (Recommended)**
1. Truy cập: `http://localhost:8080/admin/data`
2. Đăng nhập với tài khoản `admin`
3. Click "Load Tất cả Dữ liệu" để import
4. Hoặc load từng loại riêng biệt

### **Phương pháp 2: H2 Console**
1. Truy cập: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:file:./data/collectx`
3. Username: `sa`, Password: (để trống)
4. Copy/paste nội dung từ `init-all-data.sql`

### **Phương pháp 3: Programmatic**
```java
@Autowired
private DataManagementService dataManagementService;

// Load tất cả
dataManagementService.loadAllDefaultData();

// Load riêng lẻ
dataManagementService.loadSpecificData("brands");

// Xem thống kê
DataCount count = dataManagementService.getDataCount();
```

## 📊 Dữ liệu có sẵn

| Loại | Số lượng | Mô tả |
|------|----------|-------|
| **Brands** | 17 | Nike, Adidas, Panini, Jordan, etc. |
| **Categories** | 5 | Sneaker, Trading Card, Figure, etc. |
| **Users** | 5 | admin, seller1, buyer1, collector, trader |
| **Products** | 23 | Sneakers, cards, figures, apparel |
| **Promotions** | 23 | Active, expired, upcoming |
| **Sales** | 54 | Historical data for AI analysis |

## 🔒 Bảo mật

- ✅ Chỉ ADMIN có quyền truy cập `/admin/data`
- ✅ Confirmation dialogs cho thao tác nguy hiểm
- ✅ Transaction rollback nếu có lỗi
- ✅ Input validation và error handling

## 🚀 Production Ready

### **PostgreSQL Deployment**
```bash
# Connect to production DB
psql -h your-host -U your-user -d your-database

# Load data
\i src/main/resources/db/init-data/init-all-data.sql
```

### **Docker Deployment**
```bash
# Copy SQL to container
docker cp src/main/resources/db/init-data/init-all-data.sql container:/tmp/

# Execute
docker exec -i container psql -U user -d database < /tmp/init-all-data.sql
```

## ⚠️ Lưu ý quan trọng

1. **Backup**: Luôn backup dữ liệu trước khi load
2. **Test**: Test trên dev environment trước
3. **Order**: Load theo thứ tự dependencies
4. **Sequences**: Tự động reset sequences
5. **Encoding**: Sử dụng UTF-8

## 🎉 Kết quả

- ✅ **Dữ liệu tách biệt khỏi code**
- ✅ **Quản lý dễ dàng qua web interface**
- ✅ **Backup và restore đơn giản**
- ✅ **Version control cho dữ liệu**
- ✅ **Production ready**
- ✅ **Không ảnh hưởng cấu trúc hiện tại**

## 📞 Troubleshooting

### **Lỗi thường gặp:**
1. **403 Forbidden**: Đăng nhập với tài khoản ADMIN
2. **SQL Error**: Kiểm tra syntax trong SQL files
3. **Foreign Key Error**: Load theo đúng thứ tự
4. **Encoding Error**: Đảm bảo UTF-8

### **Logs để debug:**
```bash
# Xem logs trong console
tail -f logs/application.log

# Hoặc trong IDE
# Console output sẽ hiển thị chi tiết
```

## 🎯 Next Steps

1. **Test trên dev environment**
2. **Deploy lên staging**
3. **Backup production data**
4. **Deploy lên production**
5. **Monitor và verify**

---

**🎊 Chúc mừng! Hệ thống quản lý dữ liệu đã sẵn sàng sử dụng!**
