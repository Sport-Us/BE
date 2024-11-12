package com.sportus.be.place.repository;

import com.sportus.be.place.application.type.SortType;
import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import com.sportus.be.place.dto.response.PlaceDetailWithReviews;
import com.sportus.be.place.dto.response.SearchPlaceResponse;
import java.util.List;

public interface PlaceRepositoryCustom {
    List<SearchPlaceResponse> findAllFacilityByLocationWithDistance(
            double longitude, double latitude, int maxDistance, List<FacilityCategory> categoryList, SortType sortType,
            String keyword, long page, long size);

    List<SearchPlaceResponse> findAllLectureByLocationWithDistance(
            double longitude, double latitude, int maxDistance, List<LectureCategory> categoryList, SortType sortType,
            String keyword, long page, long size);

    PlaceDetailWithReviews findPlaceDetail(Long placeId, Long userId, Long size);
}
