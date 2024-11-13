package com.sportus.be.cardnews.presentation;

import com.sportus.be.cardnews.application.CardNewsService;
import com.sportus.be.cardnews.dto.response.CardNewsDetailResponse;
import com.sportus.be.cardnews.dto.response.CardNewsResponseList;
import com.sportus.be.global.dto.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CardNews", description = "카드뉴스 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/card-news")
public class CardNewsController {

    private final CardNewsService cardNewsService;

    // 카드뉴스 리스트 조회
    @Operation(summary = "카드뉴스 리스트 조회", description = "카드뉴스 리스트 조회")
    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getCardNewsList() {

        CardNewsResponseList response = CardNewsResponseList.from(cardNewsService.getCardNewsList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    // 카드뉴스 상세 조회
    @Operation(summary = "카드뉴스 상세 조회", description = "카드뉴스 상세 조회")
    @GetMapping("/{cardNewsId}")
    public ResponseEntity<ResponseTemplate<?>> getCardNewsDetail(
            @PathVariable Long cardNewsId) {

        CardNewsDetailResponse response = CardNewsDetailResponse.from(cardNewsService.getCardNewsDetail(cardNewsId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}