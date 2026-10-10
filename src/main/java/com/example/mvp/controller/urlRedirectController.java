package com.example.mvp.controller;

import com.example.mvp.entity.urlMapping;
import com.example.mvp.repository.urlMappingRepository;
import com.example.mvp.service.urlMappingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class urlRedirectController {
    private final urlMappingService urlMappingService;

    public urlRedirectController(urlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortCode){
        urlMapping urlMapping = urlMappingService.getByShortCode(shortCode);
        URI original = URI.create(urlMapping.getOriginalUrl());

        return ResponseEntity.status(HttpStatus.FOUND).location(original).build();
    }
}
