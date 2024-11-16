package com.sportus.be.recommend.repository.mongo;

import com.sportus.be.recommend.domain.MongoUser;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserRepository extends MongoRepository<MongoUser, String> {

    @Query(value = "{ 'aiUserOnboardingInfoList': { $elemMatch: { 'onboarding_type': ?0, 'content': ?1 } } }",
            fields = "{ 'aiSearchInfoList': 1 , 'user_id': 1 }")
    List<MongoUser> findByOnboardingInfo(String onboardingType, String content);
}
