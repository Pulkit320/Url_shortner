package com.example.mvp.service;

import com.example.mvp.entity.urlMapping;
import com.example.mvp.repository.urlMappingRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        while (true){
            String shortCode=generateShortCode();
            if(urlMappingRepository.existsByShortCode(shortCode)){
                continue;
            }
            urlMapping urlMapping=new urlMapping(OriginalUrl,shortCode);

            try{
                return urlMappingRepository.save(urlMapping);
            }catch (DataIntegrityViolationException e){
                if(urlMappingRepository.existsByShortCode(shortCode)){
                    continue;
                }
                throw e;
            }
        }
    }

    private String generateShortCode(){
        StringBuilder sb = new StringBuilder();
        for(int i= 0;i<6;i++){
            int idx = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(idx));
        }
        return sb.toString();
    }

    public urlMapping getByShortCode(String shortCode){
        return urlMappingRepository.findByShortCode(shortCode).
                orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Short URL not found!"
                ));
    }
}
