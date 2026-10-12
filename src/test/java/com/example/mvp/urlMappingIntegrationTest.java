package com.example.mvp;

import com.example.mvp.entity.urlMapping;
import com.example.mvp.repository.urlMappingRepository;
import com.example.mvp.service.urlMappingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class urlMappingIntegrationTest {
    @Autowired
    private urlMappingService urlMappingService;

    @Autowired
    private urlMappingRepository urlMappingRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shortenUrlShouldPersistMappingInDatabase(){
        String originalUrl = "https://www.wikipedia.org";
        urlMapping savedMapping = urlMappingService.shortenUrl(originalUrl);

        assertNotNull(savedMapping.getId());
        assertNotNull(savedMapping.getShortCode());
        assertEquals(originalUrl,savedMapping.getOriginalUrl());

        urlMapping retrievedMapping = urlMappingRepository.findByShortCode(savedMapping.getShortCode()).orElseThrow();

        assertEquals(savedMapping.getId(),retrievedMapping.getId());
        assertEquals(originalUrl,retrievedMapping.getOriginalUrl());
    }

    @Test
    void postUrlShouldReturnResponseAndPersistMapping() throws Exception{
        String originalUrl = "https://www.wikipedia.org";
        String response = mockMvc.perform(post("/api/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"originalUrl" : "https://www.wikipedia.org"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value(originalUrl))
                .andExpect(jsonPath("$.shortCode").isNotEmpty())
                .andExpect(jsonPath("$.shortUrl").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String shortCode = JsonMapper
                .builder()
                .build()
                .readTree(response)
                .get("shortCode")
                .asText();

        urlMapping savedMapping = urlMappingRepository.findByShortCode(shortCode).orElseThrow();

        assertNotNull(originalUrl,savedMapping.getOriginalUrl());

    }
}
