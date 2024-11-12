package com.sportus.be.place.dto.response;

import com.sportus.be.place.application.type.SortType;
import java.util.List;
import lombok.Builder;

@Builder
public record SearchPlaceResponseList(
        Long page,
        Boolean hasNext,
        Double longitude,
        Double latitude,
        Integer maxDistance,
        String keyWord,
        SortType sortType,
        List<String> categoryList,
        List<SearchPlaceResponse> placeList
) {
    public static SearchPlaceResponseList of(
            Long page, Boolean hasNext, Double longitude, Double latitude, Integer maxDistance, String keyWord,
            SortType sortType, List<String> categoryList, List<SearchPlaceResponse> placeList) {
        return SearchPlaceResponseList.builder()
                .page(page)
                .placeList(placeList)
                .longitude(longitude)
                .latitude(latitude)
                .hasNext(hasNext)
                .maxDistance(maxDistance)
                .keyWord(keyWord)
                .sortType(sortType)
                .categoryList(categoryList)
                .build();
    }
}
