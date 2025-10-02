package org.example.demo_j5_asm1.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    private String country;
    @Column(nullable = false, unique = true)
    private String slug;
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;
}