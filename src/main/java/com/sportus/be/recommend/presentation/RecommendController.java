package com.sportus.be.recommend.presentation;

import com.sportus.be.global.dto.ResponseTemplate;
import com.sportus.be.place.dto.response.SearchPlaceResponseList;
import com.sportus.be.recommend.application.AIService;
import com.sportus.be.recommend.application.AIServiceFacade;
import com.sportus.be.recommend.dto.request.OnboardingAnalysisInfoList;
import com.sportus.be.recommend.dto.response.MongoUserResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Recommend", description = "추천 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recommend")
public class RecommendController {

    private final AIService aiService;
    private final AIServiceFacade aiServiceFacade;

    @Operation(summary = "장소 추천", description =
            "현재 위치만 입력 & 다음 페이징은 /places/search/facilities 사용<br>" +
                    "예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116")
    @GetMapping("/search/facilities")
    public ResponseEntity<ResponseTemplate<?>> recommendFacilities(
            @AuthenticationPrincipal Long userId,
            @RequestParam Double longitude,
            @RequestParam Double latitude) {

        SearchPlaceResponseList nearestPlaces =
                aiServiceFacade.searchRecommendPlaces(userId, true, longitude, latitude);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(nearestPlaces));
    }

    @Operation(summary = "강좌 추천", description =
            "현재 위치만 입력 & 다음 페이징은 /places/search/lectures 사용<br>" +
                    "예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116")
    @GetMapping("/search/lectures")
    public ResponseEntity<ResponseTemplate<?>> recommendLectures(
            @AuthenticationPrincipal Long userId,
            @RequestParam Double longitude,
            @RequestParam Double latitude) {

        SearchPlaceResponseList nearestPlaces =
                aiServiceFacade.searchRecommendPlaces(userId, false, longitude, latitude);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(nearestPlaces));
    }

    @Operation(summary = "모든 MongoUser 가져오기", description = "ai 분석을 위해 사용하니 신경쓰지 않으셔도 됩니다!")
    @PostMapping("/mongo-users")
    public MongoUserResponseList getAllMongoUsers(
            @RequestBody OnboardingAnalysisInfoList onboardingInfos
    ) {
        return MongoUserResponseList.from(aiService.getMongoUsers(onboardingInfos.onboardingAnalysisInfoList()));
    }
}
