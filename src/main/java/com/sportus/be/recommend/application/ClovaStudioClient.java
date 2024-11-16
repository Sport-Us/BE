package com.sportus.be.recommend.application;

import com.sportus.be.recommend.dto.request.AIRecommendRequest;
import com.sportus.be.recommend.dto.response.ClovaAnalysisResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "clovaStudioClient", url = "${clova.api-url}")
public interface ClovaStudioClient {

    @PostMapping(value = "${clova.api-path}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ClovaAnalysisResponse getFinalAnswer(
            @RequestHeader("X-NCP-CLOVASTUDIO-API-KEY") String clovaApiKey,
            @RequestHeader("X-NCP-APIGW-API-KEY") String apiGwApiKey,
            @RequestHeader("X-NCP-CLOVASTUDIO-REQUEST-ID") String requestId,
            @RequestBody AIRecommendRequest requestBody
    );
}
