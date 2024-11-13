package com.sportus.be.cardnews.repository;

import com.sportus.be.cardnews.domain.Card;
import com.sportus.be.cardnews.domain.CardNews;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByCardNews(CardNews cardNews);
}
