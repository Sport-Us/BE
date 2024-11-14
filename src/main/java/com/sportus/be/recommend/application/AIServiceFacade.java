package com.sportus.be.recommend.application;

import com.sportus.be.place.application.PlaceService;
import com.sportus.be.place.application.type.SortType;
import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import com.sportus.be.place.dto.response.AIRecommendResponse;
import com.sportus.be.place.dto.response.SearchPlaceResponseList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIServiceFacade {

    private final AIService aiService;
    private final PlaceService placeService;

    public SearchPlaceResponseList searchRecommendFacilityPlaces(Long userId, double longitude, double latitude) {
        AIRecommendResponse recommendation = aiService.getFacilityRecommendation(userId);

        SortType sortType = SortType.fromString(recommendation.sortType());
        List<FacilityCategory> categories = recommendation.categories().stream()
                .map(FacilityCategory::fromString)
                .toList();

        return placeService.searchFacilityPlaces(
                longitude, latitude, recommendation.maxDistance(), categories, sortType, "", 0, 10);
    }

    public SearchPlaceResponseList searchRecommendLecturePlaces(Long userId, double longitude, double latitude) {
        AIRecommendResponse recommendation = aiService.getLectureRecommendation(userId);

        SortType sortType = SortType.fromString(recommendation.sortType());
        List<LectureCategory> categories = recommendation.categories().stream()
                .map(LectureCategory::fromString)
                .toList();

        return placeService.searchLecturePlaces(
                longitude, latitude, recommendation.maxDistance(), categories, sortType, "", 0, 10);
    }
}
