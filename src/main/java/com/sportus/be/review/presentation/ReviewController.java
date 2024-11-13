package com.sportus.be.review.presentation;

import com.sportus.be.global.dto.ResponseTemplate;
import com.sportus.be.review.application.ReviewService;
import com.sportus.be.review.dto.request.ReviewUploadRequest;
import com.sportus.be.review.dto.response.ReviewSimpleResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Review", description = "리뷰 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 리스트 조회
    @Operation(summary = "리뷰 리스트 조회", description = "장소별 리뷰 리스트 조회, lastReviewId를 기준으로 이전 리뷰 조회, 리뷰는 최신순으로 정렬<br>" +
            "lastReviewId 이전(시간순서) review부터 size만큼 리뷰를 가져옴, place 8475번이 더미 데이터 존재 <br>"
            + "/places/detail/{placeId} API를 통해 리뷰를 확인할 수 있음 - 해당 api 다음에 이 API를 호출해야함")
    @GetMapping("/{placeId}")
    public ResponseEntity<ResponseTemplate<?>> getReviewList(
            @PathVariable Long placeId,
            @RequestParam Long lastReviewId,
            @RequestParam(defaultValue = "5") Long size) {

        ReviewSimpleResponseList reviewSimpleResponse =
                reviewService.getReviewSimpleResponse(placeId, lastReviewId, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(reviewSimpleResponse));
    }

    // 리뷰 업로드
    @Operation(summary = "리뷰 업로드", description = "리뷰 업로드, 리뷰 이미지 업로드 후 리뷰 저장")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseTemplate<?>> uploadReview(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestPart ReviewUploadRequest reviewUploadRequest,
            @RequestPart(required = false) MultipartFile file) {

        reviewService.uploadReview(userId, reviewUploadRequest, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    // 리뷰 삭제
    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제, 본인의 리뷰만 삭제 가능")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ResponseTemplate<?>> deleteReview(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long reviewId) {

        reviewService.deleteReview(userId, reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
