# Hệ thống Phân quyền Đơn hàng

## Tổng quan
Hệ thống đã được cập nhật để đảm bảo quyền hạn chính xác cho việc chuyển đổi trạng thái đơn hàng.

## Quyền hạn theo Role

### Người bán (SELLER)
**Chỉ có thể chuyển đổi trạng thái theo thứ tự:**
- `PENDING` → `CONFIRMED` (Xác nhận đơn hàng)
- `CONFIRMED` → `SHIPPED` (Gửi hàng)
- `SHIPPED` → `DELIVERED` (Giao hàng)
- `PENDING` → `CANCELLED` (Hủy đơn hàng)
- `CONFIRMED` → `CANCELLED` (Hủy đơn hàng)

**Không thể:**
- Bỏ qua bước (ví dụ: PENDING → SHIPPED)
- Chuyển từ DELIVERED, COMPLETED, CANCELLED sang trạng thái khác

### Người mua (BUYER)
**Chỉ có thể:**
- `DELIVERED` → `COMPLETED` (Hoàn tất đơn hàng)
- Hủy đơn hàng khi trạng thái là PENDING hoặc CONFIRMED

**Không thể:**
- Cập nhật trạng thái khác ngoài COMPLETED
- Hoàn tất đơn hàng chưa được giao (DELIVERED)

### Admin (ADMIN)
- Có thể thực hiện tất cả các thao tác của cả SELLER và BUYER

## Luồng xử lý

### 1. Tạo đơn hàng
```
BUYER tạo đơn hàng → Trạng thái: PENDING
```

### 2. Xử lý đơn hàng (SELLER)
```
PENDING → CONFIRMED → SHIPPED → DELIVERED
```

### 3. Hoàn tất đơn hàng (BUYER)
```
DELIVERED → COMPLETED
```

## Kiểm tra trong Code

### OrderService.validateStatusTransition()
- Kiểm tra chuyển đổi trạng thái có hợp lệ không
- Đảm bảo tuân thủ quy tắc phân quyền

### OrderService.updateOrderStatus()
- Kiểm tra quyền sở hữu đơn hàng
- Kiểm tra role của user
- Validate chuyển đổi trạng thái

### OrderService.completeOrder()
- Kiểm tra quyền sở hữu đơn hàng
- Kiểm tra role của user
- Đảm bảo trạng thái hiện tại là DELIVERED

## Ví dụ sử dụng

### Cập nhật trạng thái (SELLER)
```java
User seller = createUser(2L, User.Role.SELLER);
orderService.updateOrderStatus(orderId, Order.OrderStatus.CONFIRMED, seller);
```

### Hoàn tất đơn hàng (BUYER)
```java
User buyer = createUser(1L, User.Role.BUYER);
orderService.completeOrder(orderId, buyer);
```

## Lỗi thường gặp

1. **"Only seller can update order status"**
   - Người dùng không phải là người bán của đơn hàng

2. **"Only sellers and admins can update order status"**
   - Role của user không phải SELLER hoặc ADMIN

3. **"From PENDING, seller can only change to CONFIRMED or CANCELLED"**
   - Cố gắng chuyển trạng thái không đúng quy tắc

4. **"Order must be delivered before completion"**
   - Cố gắng hoàn tất đơn hàng chưa được giao

## Cải tiến trong tương lai

1. **Session Management**: Thay thế hardcode user bằng session/security
2. **Audit Log**: Ghi lại lịch sử thay đổi trạng thái
3. **Notification**: Thông báo cho người dùng khi trạng thái thay đổi
4. **Time-based Rules**: Thêm quy tắc về thời gian (ví dụ: tự động hủy sau 24h)
