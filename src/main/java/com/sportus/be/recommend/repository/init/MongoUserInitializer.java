    package com.sportus.be.recommend.repository.init;

    import com.sportus.be.place.domain.type.FacilityCategory;
    import com.sportus.be.place.domain.type.LectureCategory;
    import com.sportus.be.recommend.domain.MongoAISearchInfo;
    import com.sportus.be.recommend.domain.MongoAIUserOnboardingInfo;
    import com.sportus.be.recommend.domain.MongoUser;
    import com.sportus.be.recommend.repository.mongo.MongoUserRepository;
    import com.sportus.be.global.util.DummyDataInit;
    import com.sportus.be.user.domain.type.OnboardingType;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.boot.ApplicationArguments;
    import org.springframework.boot.ApplicationRunner;
    import org.springframework.core.annotation.Order;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Random;
    import java.util.stream.Collectors;
    import java.util.stream.IntStream;

    @Slf4j
    @RequiredArgsConstructor
    @Order(1)
    @DummyDataInit
    public class MongoUserInitializer implements ApplicationRunner {

        private final MongoUserRepository mongoUserRepository;
        private final Random random = new Random();

        @Override
        public void run(ApplicationArguments args) {
            if (mongoUserRepository.count() > 0) {
                log.info("[MongoUser] 더미 데이터 존재");
            } else {
                List<MongoUser> mongoUsers = createDummyUsers(30);
                mongoUserRepository.saveAll(mongoUsers);
            }
        }

        private List<MongoUser> createDummyUsers(int count) {
            return IntStream.rangeClosed(1, count)
                    .mapToObj(i -> MongoUser.builder()
                            .userId((long) i)
                            .aiSearchInfoList(getMongoAISearchInfoList(i))
                            .aiUserOnboardingInfoList(getMongoAIUserOnboardingInfoList(i))
                            .build())
                    .collect(Collectors.toList());
        }

        private List<MongoAISearchInfo> getMongoAISearchInfoList(int userId) {
            List<MongoAISearchInfo> mongoAISearchInfoList = new ArrayList<>();

            // FacilityCategory와 LectureCategory의 조합을 위한 랜덤 선택
            List<FacilityCategory> facilityCategories = List.of(FacilityCategory.values());
            List<LectureCategory> lectureCategories = List.of(LectureCategory.values());

            for (int i = 1; i <= 3; i++) {
                // 랜덤으로 시설 카테고리 선택
                FacilityCategory facilityCategory = facilityCategories.get(random.nextInt(facilityCategories.size()));

                // 랜덤으로 강의 카테고리 선택
                LectureCategory lectureCategory = lectureCategories.get(random.nextInt(lectureCategories.size()));

                mongoAISearchInfoList.add(MongoAISearchInfo.builder()
                        .placeId((long) (userId * 100 + i))
                        .isFacility(i % 2 == 0) // 짝수 인덱스는 시설로 설정
                        .placeCategory(i % 2 == 0 ? facilityCategory.name() : lectureCategory.name())
                        .build());
            }
            return mongoAISearchInfoList;
        }


        private List<MongoAIUserOnboardingInfo> getMongoAIUserOnboardingInfoList(int userId) {
            List<MongoAIUserOnboardingInfo> mongoAIUserOnboardingInfoList = new ArrayList<>();

            // 관심사 더미 데이터
            List<LectureCategory> lectureCategories = List.of(LectureCategory.values());
            for (int i = 0; i < 2; i++) {
                mongoAIUserOnboardingInfoList.add(MongoAIUserOnboardingInfo.builder()
                        .onboardingType(OnboardingType.INTEREST.name())
                        .content(lectureCategories.get(random.nextInt(lectureCategories.size())).getName())
                        .build());
            }

            // 목적 더미 데이터 (1~2개 랜덤 선택)
            String[] purposes = {
                    "다이어트", "근육 강화", "취미 및 여가 활동",
                    "재활", "스트레스 해소", "대회 준비"
            };
            int purposeCount = random.nextInt(2) + 1; // 1~2개 선택
            List<String> selectedPurposes = new ArrayList<>(List.of(purposes));
            for (int i = 0; i < purposeCount; i++) {
                int randomIndex = random.nextInt(selectedPurposes.size());
                mongoAIUserOnboardingInfoList.add(MongoAIUserOnboardingInfo.builder()
                        .onboardingType(OnboardingType.PURPOSE.name())
                        .content(selectedPurposes.remove(randomIndex)) // 선택 후 목록에서 제거
                        .build());
            }

            // 선호도 더미 데이터 (1~2개 랜덤 선택)
            String[] preferences = {
                    "가까운 곳이 좋아요", "먼 곳도 괜찮아요",
                    "공공시설이 좋아요", "샤워 및 탈의실이 잘 갖춰진 곳이 좋아요",
                    "주차가 편리한 곳이 좋아요", "혼자 하는 활동이 좋아요"
            };
            int preferenceCount = random.nextInt(2) + 1; // 1~2개 선택
            List<String> selectedPreferences = new ArrayList<>(List.of(preferences));
            for (int i = 0; i < preferenceCount; i++) {
                int randomIndex = random.nextInt(selectedPreferences.size());
                mongoAIUserOnboardingInfoList.add(MongoAIUserOnboardingInfo.builder()
                        .onboardingType(OnboardingType.PREFERENCE.name())
                        .content(selectedPreferences.remove(randomIndex)) // 선택 후 목록에서 제거
                        .build());
            }

            return mongoAIUserOnboardingInfoList;
        }
    }
