package org.example.demo_j5_asm1.repository;

import java.util.Optional;

import org.example.demo_j5_asm1.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findBySlug(String slug);
    Optional<Brand> findByName(String name);
}
