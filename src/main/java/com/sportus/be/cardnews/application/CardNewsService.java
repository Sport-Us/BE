package com.sportus.be.cardnews.application;

import static com.sportus.be.cardnews.exception.errorcode.CardNewsErrorCode.CARD_NEWS_NOT_FOUND;

import com.sportus.be.cardnews.domain.CardNews;
import com.sportus.be.cardnews.dto.response.CardNewsResponse;
import com.sportus.be.cardnews.dto.response.CardResponse;
import com.sportus.be.cardnews.exception.CardNewsNotFoundException;
import com.sportus.be.cardnews.repository.CardNewsRepository;
import com.sportus.be.cardnews.repository.CardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardNewsService {

    private final CardNewsRepository cardNewsRepository;
    private final CardRepository cardRepository;

    public List<CardNewsResponse> getCardNewsList() {
        return cardNewsRepository.findAll().stream()
                .map(CardNewsResponse::from)
                .toList();
    }

    public List<CardResponse> getCardNewsDetail(Long cardNewsId) {
        CardNews cardNews = getCardNews(cardNewsId);

        return cardRepository.findAllByCardNews(cardNews).stream()
                .map(CardResponse::from)
                .toList();
    }

    private CardNews getCardNews(Long cardNewsId) {
        return cardNewsRepository.findById(cardNewsId)
                .orElseThrow(() -> new CardNewsNotFoundException(CARD_NEWS_NOT_FOUND));
    }
}