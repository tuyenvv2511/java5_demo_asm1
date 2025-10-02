# CollectX (Spring Boot 3)

- CRUD Brand + Product (quan hệ N-1), filter Brand/Category/Search.
- WebSocket chat theo sản phẩm.
- i18n (vi/en) qua `?lang=`.
- H2 (dev) + seed `data.sql`; Postgres (prod).

## Chạy local

```bash
./mvnw spring-boot:run
# hoặc: mvn spring-boot:run
# mở http://localhost:8080
