package com.sportus.be.recommend.repository.mongo;

import com.sportus.be.recommend.domain.MongoUser;
import com.sportus.be.recommend.dto.request.OnboardingAnalysisInfo;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserRepositoryCustom {

    List<MongoUser> findByOnboardingInfo(List<OnboardingAnalysisInfo> onboardingAnalysisInfoList);
}
