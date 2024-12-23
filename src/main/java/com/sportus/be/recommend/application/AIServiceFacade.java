package com.sportus.be.recommend.application;

import com.sportus.be.place.application.PlaceService;
import com.sportus.be.place.application.type.SortType;
import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import com.sportus.be.place.dto.response.SearchPlaceResponseList;
import com.sportus.be.recommend.dto.response.ClovaAnalysisResponse;
import java.util.ArrayList;
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

    public SearchPlaceResponseList searchRecommendPlaces(
            Long userId, boolean isFacility, double longitude, double latitude
    ) {
        ClovaAnalysisResponse recommendation = aiService.getRecommendation(userId, isFacility);

        if (isFacility) {
            return searchFacilities(longitude, latitude, recommendation);
        } else {
            return searchLectures(longitude, latitude, recommendation);
        }
    }

    private SearchPlaceResponseList searchFacilities(double longitude, double latitude,
                                                     ClovaAnalysisResponse recommendation) {
        List<FacilityCategory> categories = recommendation.categories().stream()
                .filter(category -> {
                    try {
                        FacilityCategory.valueOf(category);
                        return true;
                    } catch (IllegalArgumentException e) {
                        return false; // 유효하지 않은 값은 무시
                    }
                })
                .map(FacilityCategory::valueOf)
                .toList();

        if (categories.isEmpty()) {
            categories = new ArrayList<>(List.of(FacilityCategory.ALL));
        }

        return placeService.searchFacilityPlaces(
                longitude, latitude, 100000, categories, SortType.DISTANCE_ASC, "", 0, 10);
    }

    private SearchPlaceResponseList searchLectures(double longitude, double latitude,
                                                   ClovaAnalysisResponse recommendation) {
        List<LectureCategory> categories = recommendation.categories().stream()
                .filter(category -> {
                    try {
                        LectureCategory.valueOf(category);
                        return true;
                    } catch (IllegalArgumentException e) {
                        return false; // 유효하지 않은 값은 무시
                    }
                })
                .map(LectureCategory::valueOf)
                .toList();

        if (categories.isEmpty()) {
            categories = new ArrayList<>(List.of(LectureCategory.ALL));
        }

        return placeService.searchLecturePlaces(
                longitude, latitude, 100000, categories, SortType.DISTANCE_ASC, "", 0, 10);
    }
}
