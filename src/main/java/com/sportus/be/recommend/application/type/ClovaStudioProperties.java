package com.sportus.be.recommend.application.type;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clova")
public record ClovaStudioProperties(
        String studioApiKey,
        String apigwApiKey,
        String requestId
) {
}
