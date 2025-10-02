package org.example.demo_j5_asm1.controller;

import org.example.demo_j5_asm1.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.*;

public class ProductSpecs {
    public static Specification<Product> byFilters(String brandSlug, String catSlug, String q) {
        return (root, cq, cb) -> {
            List<Predicate> ps = new ArrayList<>();
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
            return cb.and(ps.toArray(new Predicate[0]));
        };
    }
}
