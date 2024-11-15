package com.sportus.be.recommend.application;

import com.sportus.be.place.dto.response.AIRecommendResponse;
import com.sportus.be.recommend.dto.response.MongoUserResponse;
import com.sportus.be.recommend.repository.mongo.MongoUserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public AIRecommendResponse getFacilityRecommendation(Long userId) {
        return null;
    }

    public AIRecommendResponse getLectureRecommendation(Long userId) {
        return null;
    }

    public List<MongoUserResponse> getAllMongoUsers() {
        List<MongoUserResponse> mongoUserResponses = new ArrayList<>(mongoUserRepository.findAll().stream()
                .map(MongoUserResponse::from)
                .toList());

        Collections.shuffle(mongoUserResponses);

        return mongoUserResponses.stream()
                .limit(15)
                .toList();
    }
}
