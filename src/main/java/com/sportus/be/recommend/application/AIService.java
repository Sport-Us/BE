package com.sportus.be.recommend.application;

import com.sportus.be.recommend.application.type.ClovaStudioProperties;
import com.sportus.be.recommend.domain.MongoAISearchInfo;
import com.sportus.be.recommend.domain.MongoUser;
import com.sportus.be.recommend.dto.request.AIRecommendRequest;
import com.sportus.be.recommend.dto.request.OnboardingAnalysisInfo;
import com.sportus.be.recommend.dto.response.ClovaAnalysisResponse;
import com.sportus.be.recommend.dto.response.MongoUserResponse;
import com.sportus.be.recommend.exception.MongoUserNotFoundException;
import com.sportus.be.recommend.exception.errorcode.RecommendErrorCode;
import com.sportus.be.recommend.repository.mongo.MongoUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AIService {

    private final MongoUserRepository mongoUserRepository;
    private final ClovaStudioProperties clovaStudioProperties;
    private final ClovaStudioClient clovaStudioClient;

    public ClovaAnalysisResponse getRecommendation(Long userId, Boolean isFacility) {
        MongoUser mongoUser = mongoUserRepository.findByUserId(userId)
                .orElseThrow(() -> new MongoUserNotFoundException(RecommendErrorCode.MONGO_USER_NOT_FOUND));

        String query = makeRecommendQuery(mongoUser, isFacility);

        return clovaStudioClient.getFinalAnswer(
                clovaStudioProperties.studioApiKey(),
                clovaStudioProperties.apigwApiKey(),
                clovaStudioProperties.requestId(),
                AIRecommendRequest.of(query, false)
        ); // 필요한 경우 최종 응답을 반환
    }

    private String makeRecommendQuery(MongoUser mongoUser, Boolean isFacility) {
        String query = mongoUser.getAiSearchInfoList().stream()
                .filter(mongoAISearchInfo -> mongoAISearchInfo.getIsFacility() == isFacility)
                .map(MongoAISearchInfo::getPlaceCategory)
                .collect(Collectors.joining(","));

        if (isFacility) {
            return query + "\n시설을 추천해줘.";
        } else {
            return query + "\n강좌를 추천해줘.";
        }
    }

    public List<MongoUserResponse> getMongoUsers(List<OnboardingAnalysisInfo> onboardingInfoList) {
        return mongoUserRepository.findByOnboardingInfo(onboardingInfoList).stream()
                .map(MongoUserResponse::from)
                .toList();
    }
}
