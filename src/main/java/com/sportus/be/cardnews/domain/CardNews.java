package com.sportus.be.cardnews.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "card_news")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CardNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "cardNews")
    private List<Card> cards = new ArrayList<>();

    @Builder
    public CardNews(String title, String content, List<Card> cards) {
        this.title = title;
        this.content = content;
        this.cards = cards;
    }
}
