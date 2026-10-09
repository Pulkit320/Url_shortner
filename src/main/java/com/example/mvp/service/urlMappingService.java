package com.example.mvp.service;

import com.example.mvp.entity.urlMapping;
import com.example.mvp.repository.urlMappingRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class urlMappingService {
    private final urlMappingRepository urlMappingRepository;
    private final SecureRandom random = new SecureRandom();

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public urlMappingService(urlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public urlMapping shortenUrl(String OriginalUrl){
        String shortCode;
        do{
            shortCode = generateShortCode();
        }while(urlMappingRepository.existsByShortCode(shortCode));

        urlMapping urlMapping = new urlMapping(OriginalUrl,shortCode);
        return urlMappingRepository.save(urlMapping);
    }

    private String generateShortCode(){
        StringBuilder sb = new StringBuilder();
        for(int i= 0;i<6;i++){
            int idx = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(idx));
        }
        return sb.toString();
    }
}
