package com.sportus.be.recommend.application;

import com.sportus.be.place.dto.response.AIRecommendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AIService {

    public AIRecommendResponse getFacilityRecommendation(Long userId) {
        return null;
    }

    public AIRecommendResponse getLectureRecommendation(Long userId) {
        return null;
    }
}
