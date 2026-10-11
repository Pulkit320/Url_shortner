package com.example.mvp.controller;

import com.example.mvp.dto.createUrlRequest;
import com.example.mvp.entity.urlMapping;
import com.example.mvp.service.urlMappingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class urlMappingControllerTest {

    @Mock
    private urlMappingService urlMappingService;

    @InjectMocks
    private urlMappingController urlMappingController;

    @Test
    void shortenUrlShouldReturnCreatedResponseData(){
        String originalUrl = "https://www.wikipedia.org";
        String shortCode = "abc123";

        urlMapping mapping  = new urlMapping(originalUrl, shortCode);

        when(urlMappingService.shortenUrl(originalUrl)).thenReturn(mapping);

        createUrlRequest request = new createUrlRequest();
        request.setOriginalUrl(originalUrl);

        var response = urlMappingController.shortenUrl(request);

        assertNotNull(response);
        assertEquals(originalUrl, response.getOriginalUrl());
        assertEquals(shortCode, response.getShortCode());
        assertEquals("http://localhost:8080/"+shortCode, response.getShortUrl());

        verify(urlMappingService).shortenUrl(originalUrl);
    }
}
