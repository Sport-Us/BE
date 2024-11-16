package com.sportus.be.recommend.repository.mongo;

import com.sportus.be.recommend.domain.MongoUser;
import com.sportus.be.recommend.dto.request.OnboardingAnalysisInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MongoUserRepositoryImpl implements MongoUserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public List<MongoUser> findByOnboardingInfo(List<OnboardingAnalysisInfo> onboardingAnalysisInfoList) {
        List<Criteria> criteriaList = new ArrayList<>();

        // 조건을 생성
        for (OnboardingAnalysisInfo info : onboardingAnalysisInfoList) {
            criteriaList.add(Criteria.where("aiUserOnboardingInfoList")
                    .elemMatch(Criteria.where("onboarding_type").is(info.onboardingType())
                            .and("content").is(info.content())));
        }

        // Aggregation을 사용하여 조건을 만족하는 문서를 필터링하고 랜덤으로 8개 샘플링
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(new Criteria().orOperator(criteriaList.toArray(new Criteria[0]))), // 조건에 맞는 문서 필터링
                Aggregation.sample(8) // 랜덤으로 8개 샘플링
        );

        // Aggregation 결과 가져오기
        AggregationResults<MongoUser> results = mongoTemplate.aggregate(aggregation, MongoUser.class, MongoUser.class);

        return results.getMappedResults();
    }
}
