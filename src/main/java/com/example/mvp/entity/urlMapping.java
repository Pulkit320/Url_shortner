package com.example.mvp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "url_mappings",
        uniqueConstraints = {
                @UniqueConstraint(name =
                    "uk_url_mapping_short_code",columnNames = {"short_code"}
                )
        }
)

public class urlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="original_url",nullable = false,length = 2048)
    private String originalUrl;

    @Column(name="short_code",nullable=false,length = 20)
    private String shortCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public urlMapping() {}

    public urlMapping(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
