package org.example.demo_j5_asm1.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.example.demo_j5_asm1.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
              select avg(s.soldPrice) from Sale s
               where s.product.brand.id = :brandId
                 and s.product.title = :title
                 and s.soldAt >= :from
                 and s.soldAt < :to
            """)
    BigDecimal avgPriceBetween(@Param("brandId") Long brandId,
                              @Param("title") String title,
                              @Param("from") LocalDateTime from,
                              @Param("to") LocalDateTime to);

    @Query("""
              select s from Sale s
               where s.product.brand.id = :brandId
                 and s.product.title = :title
                 and s.soldAt >= :from
               order by s.soldAt desc
            """)
    List<Sale> findRecentSales(@Param("brandId") Long brandId,
                              @Param("title") String title,
                              @Param("from") LocalDateTime from);

    @Query("""
              select s from Sale s
               where s.soldAt >= :from
               order by s.soldAt desc
            """)
    List<Sale> findRecentSalesGlobally(@Param("from") LocalDateTime from);

    @Query("""
              select s.product.brand.name as brand, 
                     s.product.title as title,
                     avg(s.soldPrice) as avgPrice,
                     count(s) as salesCount
               from Sale s
               where s.soldAt >= :from
               group by s.product.brand.name, s.product.title
               having count(s) >= 2
               order by avgPrice desc
            """)
    List<Object[]> getTopSellingProducts(@Param("from") LocalDateTime from);
}