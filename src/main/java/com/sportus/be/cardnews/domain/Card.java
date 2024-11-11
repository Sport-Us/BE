package com.sportus.be.cardnews.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "card")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "card_image_url", nullable = false)
    private String cardImageUrl;

    @JoinColumn(name = "card_news_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CardNews cardNews;

    @Builder
    public Card(String cardImageUrl, CardNews cardNews) {
        this.cardImageUrl = cardImageUrl;
        this.cardNews = cardNews;
    }
}