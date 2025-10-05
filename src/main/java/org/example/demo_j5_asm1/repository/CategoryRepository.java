package org.example.demo_j5_asm1.repository;

import java.util.Optional;

import org.example.demo_j5_asm1.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug(String slug);
    Optional<Category> findByName(String name);
}
