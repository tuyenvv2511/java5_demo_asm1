package org.example.demo_j5_asm1.repository;

import org.example.demo_j5_asm1.entity.Sale;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("""
              select avg(s.soldPrice) from Sale s
               where s.product.brand.id = :brandId
                 and s.product.title = :title
                 and s.soldAt >= :from
            """)
    BigDecimal avgPriceLastDays(@Param("brandId") Long brandId,
                                @Param("title") String title,
                                @Param("from") LocalDateTime from);
}