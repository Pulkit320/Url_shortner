package com.example.mvp.repository;

import com.example.mvp.entity.urlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface urlMappingRepository extends JpaRepository<urlMapping, Long> {
    Optional<urlMapping> findByShortCode(String shortCode);
    public boolean existsByShortCode(String shortCode);
}
