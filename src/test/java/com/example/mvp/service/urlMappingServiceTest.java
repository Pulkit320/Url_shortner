package com.example.mvp.service;

import com.example.mvp.entity.urlMapping;
import com.example.mvp.repository.urlMappingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class urlMappingServiceTest {

    @Mock
    private urlMappingRepository urlMappingRepository;

    @InjectMocks
    private urlMappingService urlMappingService;

    @Test
    void shortenUrlShouldSaveMappingWithGeneratedCode(){
        when(urlMappingRepository.existsByShortCode(anyString()))
                .thenReturn(false);

        when(urlMappingRepository.save(any(urlMapping.class)))
                .thenAnswer(invocation->(urlMapping) invocation.getArgument(0));

        urlMapping result = urlMappingService.shortenUrl(
                "https://wikipedia.org/"
        );

        assertNotNull(result);
        assertEquals("https://wikipedia.org/",result.getOriginalUrl());
        assertNotNull(result.getShortCode());
        assertEquals(6,result.getShortCode().length());

        verify(urlMappingRepository).save(any(urlMapping.class));
    }


    @Test
    void shortenUrlShouldRetryWhenCodeCollides(){
        when(urlMappingRepository.existsByShortCode(anyString())).thenReturn(true, false);
        when(urlMappingRepository.save(any(urlMapping.class))).thenAnswer(invocation->(urlMapping) invocation.getArgument(0));
        urlMapping result = urlMappingService.shortenUrl("https://wikipedia.org/");
        assertNotNull(result);
        assertEquals(6,result.getShortCode().length());
        verify(urlMappingRepository,times(2)).existsByShortCode(anyString());

        verify(urlMappingRepository).save(any(urlMapping.class));
    }

    @Test
    void getShortCodeShouldReturnMappingWhenFound(){
        urlMapping mapping = new urlMapping(
                "https://www.wikipedia.org",
                "abc123"
        );

        when(urlMappingRepository.findByShortCode("abc123")).thenReturn(Optional.of(mapping));

        urlMapping result = urlMappingService.getByShortCode("abc123");

        assertNotNull(result);
        assertEquals("abc123",result.getShortCode());
        assertEquals("https://www.wikipedia.org",result.getOriginalUrl());

        verify(urlMappingRepository).findByShortCode("abc123");
    }

    @Test
    void getShortCodeShouldThrowExceptionWhenNotFound(){
        when(urlMappingRepository.findByShortCode("missing")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()->urlMappingService.getByShortCode("missing"));
        assertEquals(404,exception.getStatusCode().value());
        verify(urlMappingRepository).findByShortCode("missing");
    }
}
