package com.sportus.be.cardnews.repository.init;

import com.sportus.be.cardnews.domain.Card;
import com.sportus.be.cardnews.domain.CardNews;
import com.sportus.be.cardnews.repository.CardNewsRepository;
import com.sportus.be.cardnews.repository.CardRepository;
import com.sportus.be.global.util.DummyDataInit;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
@RequiredArgsConstructor
@DummyDataInit
public class CardNewsInitializer implements ApplicationRunner {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final CardNewsRepository cardNewsRepository;
    private final CardRepository cardRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (cardNewsRepository.count() > 0 || cardRepository.count() > 0) {
            log.info("[CardNews]더미 데이터 존재");
        } else {
            List<CardNews> cardNewsList = new ArrayList<>();

            CardNews cardNews1 = CardNews.builder()
                    .title("생애주기 운동백서 - 5세 이상 어린이")
                    .build();

            CardNews cardNews2 = CardNews.builder()
                    .title("생애주기 운동백서 - 만성 질환이 있는 겅인과 노인")
                    .build();

            CardNews cardNews3 = CardNews.builder()
                    .title("생애주기 운동백서 - 신체적 장애를 가진 성인")
                    .build();

            CardNews cardNews4 = CardNews.builder()
                    .title("생애주기 운동백서 - 성장기인 청소년")
                    .build();

            CardNews cardNews5 = CardNews.builder()
                    .title("생애주기 운동백서 - 임산부")
                    .build();

            CardNews cardNews6 = CardNews.builder()
                    .title("생애주기 운동백서 - 65세 이상 노인")
                    .build();

            CardNews cardNews7 = CardNews.builder()
                    .title("생애주기 운동백서 - 활동량이 많은 어린이")
                    .build();

            CardNews cardNews8 = CardNews.builder()
                    .title("생애주기 운동백서 - 운동이 부족한 직장인")
                    .build();
            cardNewsList.add(cardNews1);
            cardNewsList.add(cardNews2);
            cardNewsList.add(cardNews3);
            cardNewsList.add(cardNews4);
            cardNewsList.add(cardNews5);
            cardNewsList.add(cardNews6);
            cardNewsList.add(cardNews7);
            cardNewsList.add(cardNews8);

            List<Card> cardList = new ArrayList<>();

            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/1-1.png", cardNews1));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/1-2.png", cardNews1));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/1-3.png", cardNews1));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/1-4.png", cardNews1));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/1-5.png", cardNews1));

            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/2-1.png", cardNews2));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/2-2.png", cardNews2));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/2-3.png", cardNews2));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/2-4.png", cardNews2));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/2-5.png", cardNews2));

            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/3-1.png", cardNews3));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/3-2.png", cardNews3));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/3-3.png", cardNews3));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/3-4.png", cardNews3));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/3-5.png", cardNews3));

            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/4-1.png", cardNews4));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/4-2.png", cardNews4));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/4-3.png", cardNews4));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/4-4.png", cardNews4));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/4-5.png", cardNews4));

            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/5-1.png", cardNews5));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/5-2.png", cardNews5));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/5-3.png", cardNews5));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/5-4.png", cardNews5));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/5-5.png", cardNews5));

            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/6-1.png", cardNews6));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/6-2.png", cardNews6));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/6-3.png", cardNews6));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/6-4.png", cardNews6));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/6-5.png", cardNews6));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/6-6.png", cardNews6));

            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/7-1.png", cardNews7));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/7-2.png", cardNews7));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/7-3.png", cardNews7));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/7-4.png", cardNews7));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/7-5.png", cardNews7));

            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/8-1.png", cardNews8));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/8-2.png", cardNews8));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/8-3.png", cardNews8));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/8-4.png", cardNews8));
            cardList.add(new Card("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/cardnews/8-5.png", cardNews8));

            cardNewsRepository.saveAll(cardNewsList);
            cardRepository.saveAll(cardList);
        }
    }
}