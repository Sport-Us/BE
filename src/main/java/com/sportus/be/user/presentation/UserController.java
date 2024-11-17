package com.sportus.be.user.presentation;

import com.sportus.be.bookmark.application.BookMarkService;
import com.sportus.be.bookmark.dto.response.BookMarkPlaceResponseList;
import com.sportus.be.global.dto.ResponseTemplate;
import com.sportus.be.review.application.ReviewService;
import com.sportus.be.review.dto.response.ReviewSimpleResponseList;
import com.sportus.be.user.application.UserService;
import com.sportus.be.user.dto.request.UserOnboardingRequestList;
import com.sportus.be.user.dto.response.MypageResponse;
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

@Tag(name = "User", description = "유저 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final BookMarkService bookMarkService;

    @Operation(summary = "유저 온보딩", description =
            "onboardingType은 INTEREST, PREFERENCE, PURPOSE 중 하나<br>" +
                    "INTEREST: 관심사, PREFERENCE: 선호도, PURPOSE: 목적<br>" +
                    "INTEREST: 태권도, 유도, 복싱, 주짓수, 검도, 합기도, 헬스, 요가, 필라테스, 크로스핏, 에어로빅, 댄스(줌바 등), 축구(풋살), 농구, 배구, 야구, 탁구, 스쿼시," +
                    " 배드민턴, 테니스, 골프, 볼링, 당구, 클라이밍, 롤러인라인, 빙상(스케이트), 기타종목, 종합체육시설, 무영(발레 등), 줄넘기, 펜싱, 수영, 승마<br>" +
                    "PREFERENCE: 가까운 곳이 좋아요, 먼 곳도 괜찮아요, 공공시설이 좋아요, 샤워 및 탈의실이 잘 갖춰진 곳이 좋아요, 주차가 편리한 곳이 좋아요, 혼자 하는 활동이 좋아요," +
                    " 여럿이 함께 하는 활동이 좋아요, 정적인 활동이 좋아요, 역동적인 활동이 좋아요, 실내에서 하는 운동이 좋아요, 야외에서 하는 운동이 좋아요, 다양한 기구를 활용하는 활동이 좋아요, 전문가의 지도가 있는 활동이 좋아요<br>" +
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

    @Operation(summary = "마이페이지 조회", description = "토큰이 없는 유저(401)의 경우 '로그인 후 사용하세요' 등의 메세지를 띄워 해주세요")
    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getMypage(
            @AuthenticationPrincipal Long userId) {

        MypageResponse response = userService.getMypage(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "북마크 조회", description = "북마크 처음 조회 시 lastBookMarkId에 0을 넣어주세요<br>" +
            "최근에 북마크를 한 순서대로 제공됩니다")
    @GetMapping("/bookmark")
    public ResponseEntity<ResponseTemplate<?>> getMyBookmark(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long lastBookMarkId,
            @RequestParam(defaultValue = "5") Long size) {

        BookMarkPlaceResponseList responseList = bookMarkService.getMyBookMark(userId, lastBookMarkId, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(responseList));
    }

    @Operation(summary = "내 리뷰 모아보기", description = "리뷰 처음 조회 시 lastReviewId에 0을 넣어주세요<br>" +
            "리뷰는 최신순으로 제공됩니다")
    @GetMapping("/review")
    public ResponseEntity<ResponseTemplate<?>> getMyReview(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long lastReviewId,
            @RequestParam(defaultValue = "5") Long size) {

        ReviewSimpleResponseList responseList = reviewService.getMyReview(userId, lastReviewId, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(responseList));
    }
}
