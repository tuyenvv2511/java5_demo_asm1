package org.example.demo_j5_asm1.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.example.demo_j5_asm1.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecs {
    public static Specification<Product> byFilters(String brandSlug, String catSlug, String q, 
                                                   BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, cq, cb) -> {
            List<Predicate> ps = new ArrayList<>();
            
            // Chỉ hiển thị sản phẩm active
            ps.add(cb.equal(root.get("active"), true));
            
            if (brandSlug != null && !brandSlug.isBlank())
                ps.add(cb.equal(root.join("brand").get("slug"), brandSlug));
            if (catSlug != null && !catSlug.isBlank())
                ps.add(cb.equal(root.join("category").get("slug"), catSlug));
            if (q != null && !q.isBlank()) {
                String like = "%" + q.toLowerCase() + "%";
                ps.add(cb.or(
                        cb.like(cb.lower(root.get("title")), like),
                        cb.like(cb.lower(root.get("description")), like)
                ));
            }
            
            // Lọc theo khoảng giá
            if (minPrice != null)
                ps.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            if (maxPrice != null)
                ps.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
                
            return cb.and(ps.toArray(Predicate[]::new));
        };
    }
}
