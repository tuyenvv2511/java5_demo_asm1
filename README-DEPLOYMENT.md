# Hướng dẫn Deploy lên Render

## Các file đã được tạo/cập nhật:

### 1. **Dockerfile** (đã sửa)
- Sửa tên file JAR từ `collectx-0.0.1-SNAPSHOT.jar` thành `demo_j5_asm1-0.0.1-SNAPSHOT.jar`

### 2. **application-prod.properties** (mới)
- Cấu hình production cho PostgreSQL
- Sử dụng environment variables từ Render
- Tối ưu hóa cho production

### 3. **render.yaml** (mới)
- Cấu hình tự động cho Render
- Bao gồm cả web service và PostgreSQL database
- Sử dụng plan miễn phí

## Cách deploy:

### Phương pháp 1: Sử dụng render.yaml (Khuyến nghị)
1. Push code lên GitHub repository
2. Vào [Render Dashboard](https://dashboard.render.com)
3. Click "New +" → "Blueprint"
4. Connect GitHub repository
5. Render sẽ tự động detect `render.yaml` và tạo services

### Phương pháp 2: Tạo thủ công
1. Tạo PostgreSQL database trước:
   - New + → PostgreSQL
   - Chọn plan Free
   - Lưu lại connection string

2. Tạo Web Service:
   - New + → Web Service
   - Connect GitHub repository
   - Cấu hình:
     - **Build Command**: `./mvnw clean package -DskipTests`
     - **Start Command**: `java -jar target/demo_j5_asm1-0.0.1-SNAPSHOT.jar`
     - **Environment**: Docker
     - **Dockerfile Path**: `./Dockerfile`

3. Thêm Environment Variables:
   - `SPRING_PROFILES_ACTIVE`: `prod`
   - `DATABASE_URL`: (từ PostgreSQL service)
   - `DB_USERNAME`: `postgres`
   - `DB_PASSWORD`: (từ PostgreSQL service)

## Lưu ý:
- Ứng dụng sẽ tự động chuyển sang PostgreSQL khi deploy
- Cấu trúc code hiện tại không bị thay đổi
- Development vẫn sử dụng H2 database như cũ
- Production sẽ sử dụng PostgreSQL trên Render
