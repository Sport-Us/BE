package com.sportus.be.recommend.repository.init;

import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import com.sportus.be.recommend.domain.MongoAISearchInfo;
import com.sportus.be.recommend.domain.MongoAIUserOnboardingInfo;
import com.sportus.be.recommend.domain.MongoUser;
import com.sportus.be.recommend.repository.mongo.MongoUserRepository;
import com.sportus.be.global.util.DummyDataInit;
import com.sportus.be.user.domain.type.OnboardingType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class MongoUserInitializer implements ApplicationRunner {

    private final MongoUserRepository mongoUserRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (mongoUserRepository.count() > 0) {
            log.info("[MongoUser]더미 데이터 존재");
        } else {
            List<MongoUser> mongoUsers = new ArrayList<>();

            MongoUser DUMMY_MONGO_USER1 = MongoUser.builder()
                    .userId(1L)
                    .aiSearchInfoList(getMongoAISearchInfoList())
                    .aiUserOnboardingInfoList(getMongoAIUserOnboardingInfoList())
                    .build();

            MongoUser DUMMY_MONGO_USER2 = MongoUser.builder()
                    .userId(2L)
                    .build();

            MongoUser DUMMY_MONGO_USER3 = MongoUser.builder()
                    .userId(3L)
                    .build();

            mongoUsers.add(DUMMY_MONGO_USER1);
            mongoUsers.add(DUMMY_MONGO_USER2);
            mongoUsers.add(DUMMY_MONGO_USER3);

            mongoUserRepository.saveAll(mongoUsers);
        }
    }

    private List<MongoAISearchInfo> getMongoAISearchInfoList() {
        List<MongoAISearchInfo> mongoAISearchInfoList = new ArrayList<>();

        MongoAISearchInfo DUMMY_MONGO_AI_SEARCH_INFO1 = MongoAISearchInfo.builder()
                .placeId(1L)
                .isFacility(true)
                .placeCategory(FacilityCategory.SCHOOL.name())
                .build();
        MongoAISearchInfo DUMMY_MONGO_AI_SEARCH_INFO2 = MongoAISearchInfo.builder()
                .placeId(501L)
                .isFacility(true)
                .placeCategory(FacilityCategory.DISABLED.name())
                .build();
        MongoAISearchInfo DUMMY_MONGO_AI_SEARCH_INFO3 = MongoAISearchInfo.builder()
                .placeId(26501L)
                .isFacility(false)
                .placeCategory(LectureCategory.TAEKWONDO.name())
                .build();

        mongoAISearchInfoList.add(DUMMY_MONGO_AI_SEARCH_INFO1);
        mongoAISearchInfoList.add(DUMMY_MONGO_AI_SEARCH_INFO2);
        mongoAISearchInfoList.add(DUMMY_MONGO_AI_SEARCH_INFO3);

        return mongoAISearchInfoList;
    }

    private List<MongoAIUserOnboardingInfo> getMongoAIUserOnboardingInfoList() {
        List<MongoAIUserOnboardingInfo> mongoAIUserOnboardingInfoList = new ArrayList<>();

        MongoAIUserOnboardingInfo DUMMY_MONGO_AI_USER_ONBOARDING_INFO1 = MongoAIUserOnboardingInfo.builder()
                .onboardingType(OnboardingType.INTEREST.name())
                .content("축구")
                .build();
        MongoAIUserOnboardingInfo DUMMY_MONGO_AI_USER_ONBOARDING_INFO2 = MongoAIUserOnboardingInfo.builder()
                .onboardingType(OnboardingType.INTEREST.name())
                .content("야구")
                .build();

        MongoAIUserOnboardingInfo DUMMY_MONGO_AI_USER_ONBOARDING_INFO3 = MongoAIUserOnboardingInfo.builder()
                .onboardingType(OnboardingType.PURPOSE.name())
                .content("사교")
                .build();

        mongoAIUserOnboardingInfoList.add(DUMMY_MONGO_AI_USER_ONBOARDING_INFO1);
        mongoAIUserOnboardingInfoList.add(DUMMY_MONGO_AI_USER_ONBOARDING_INFO2);
        mongoAIUserOnboardingInfoList.add(DUMMY_MONGO_AI_USER_ONBOARDING_INFO3);

        return mongoAIUserOnboardingInfoList;
    }
}
