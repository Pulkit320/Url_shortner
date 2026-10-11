package com.example.mvp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class createUrlRequest {
    @NotBlank(message = "Original URL must not be blank")
    @Pattern(
            regexp = "^https?://\\S+$",
            message = "Original URL must start with http:// or https:// and have no white spaced"
    )
    private String originalUrl;

    public createUrlRequest() {
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
