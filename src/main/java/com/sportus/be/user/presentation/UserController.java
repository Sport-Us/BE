package com.sportus.be.user.presentation;

import com.sportus.be.global.dto.ResponseTemplate;
import com.sportus.be.user.application.UserService;
import com.sportus.be.user.dto.request.UserOnboardingRequestList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 온보딩", description =
            "onboardingType은 INTEREST, PREFERENCE, PURPOSE 중 하나<br>" +
                    "INTEREST: 관심사, PREFERENCE: 선호도, PURPOSE: 목적<br>" +
                    "INTEREST: 태권도, 유도, 복싱, 주짓수, 검도, 합기도, 헬스, 요가, 필라테스, 크로스핏, 에어로빅, 댄스(줌바 등), 축구(풋살), 농구, 배구, 야구, 탁구, 스쿼시, 배드민턴, 테니스, 골프, 볼링, 당구, 클라이밍, 롤러인라인, 빙상(스케이트), 기타종목, 종합체육시설, 무영(발레 등), 줄넘기, 펜싱, 수영, 승마<br>" +
                    "PREFERENCE: 가까운 곳이 좋아요, 먼 곳도 괜찮아요, 공공시설이 좋아요, 샤워 및 탈의실이 잘 갖춰진 곳이 좋아요, 주차가 편리한 곳이 좋아요, 혼자 하는 활동이 좋아요<br>" +
                    "PURPOSE: 다이어트, 근육 강화, 취미 및 여가 활동, 재활, 스트레스 해소, 대회 준비")
    @PostMapping("/onboarding")
    public ResponseEntity<ResponseTemplate<?>> onboarding(
            @AuthenticationPrincipal Long userId,
            @RequestBody UserOnboardingRequestList userOnboardingRequestList
    ) {

        userService.onboarding(userId, userOnboardingRequestList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
