package com.example.mvp.controller;

import com.example.mvp.exception.GlobalExceptionHandler;
import com.example.mvp.service.urlMappingService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(urlMappingController.class)
@Import(GlobalExceptionHandler.class)
public class urlMappingControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private urlMappingService urlMappingService;

    @Test
    void blankOriginalUrlShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"originalUrl":" "}
                        """
                ))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Original URL must not be blank"));
    }

    @Test
    void invalidUrlFormatShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"originalUrl":"www.wikipedia.org"}
                        """
                ))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Original URL must start with http:// or https:// and have no white spaced"));

    }
}
