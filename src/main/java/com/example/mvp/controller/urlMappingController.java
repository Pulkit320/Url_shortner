package com.example.mvp.controller;

import com.example.mvp.dto.createUrlRequest;
import com.example.mvp.dto.createUrlResponse;
import com.example.mvp.entity.urlMapping;
import com.example.mvp.service.urlMappingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/urls")
public class urlMappingController {
    private final urlMappingService urlMappingService;

    public urlMappingController(urlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @PostMapping
    public createUrlResponse shortenUrl(@Valid @RequestBody createUrlRequest req){
        String originalUrl = req.getOriginalUrl();

        urlMapping urlMapping = urlMappingService.shortenUrl(originalUrl);

        String shortUrl = "http://localhost:8080/"+urlMapping.getShortCode();

        return new createUrlResponse(
                urlMapping.getOriginalUrl(),
                urlMapping.getShortCode(),
                shortUrl
        );
    }

}
