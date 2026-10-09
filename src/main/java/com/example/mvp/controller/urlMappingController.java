package com.example.mvp.controller;

import com.example.mvp.entity.urlMapping;
import com.example.mvp.service.urlMappingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/urls")
public class urlMappingController {
    private final urlMappingService urlMappingService;

    public urlMappingController(urlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @PostMapping
    public Map<String,String> shortenUrl(@RequestBody Map<String,String> req){
        String originalUrl = req.get("originalUrl");

        urlMapping urlMapping = urlMappingService.shortenUrl(originalUrl);

        String shortUrl = "http://localhost:8080/"+urlMapping.getShortCode();

        return Map.of(
                "originalUrl",urlMapping.getOriginalUrl(),
                "shortCode",urlMapping.getShortCode(),
                "shortUrl",shortUrl
        );
    }

}
