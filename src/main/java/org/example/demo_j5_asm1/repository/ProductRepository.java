package org.example.demo_j5_asm1.repository;

import java.util.List;
import java.util.Optional;

import org.example.demo_j5_asm1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByTitle(String title);
    List<Product> findByActiveTrue(); // Chỉ lấy sản phẩm active
}
