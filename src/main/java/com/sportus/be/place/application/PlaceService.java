package com.sportus.be.place.application;

import com.sportus.be.place.application.type.SortType;
import com.sportus.be.place.domain.Place;
import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import com.sportus.be.place.dto.response.MapPlaceResponse;
import com.sportus.be.place.dto.response.PlaceDetailWithReviews;
import com.sportus.be.place.dto.response.SearchPlaceResponse;
import com.sportus.be.place.dto.response.SearchPlaceResponseList;
import com.sportus.be.place.exception.PlaceNotFoundException;
import com.sportus.be.place.exception.errorcode.PlaceErrorCode;
import com.sportus.be.place.repository.PlaceRepository;
import com.sportus.be.place.repository.mongo.MongoPlaceRepository;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlaceService {

    private final MongoPlaceRepository mongoPlaceRepository;
    private final PlaceRepository placeRepository;

    // 주변 시설 검색
    public List<MapPlaceResponse> findFacilitiesWithinRadius(
            double longitude, double latitude, double radius, List<FacilityCategory> category) {

        return mongoPlaceRepository.findAllFacilityByLocationNear(longitude, latitude, radius, category).stream()
                .map(MapPlaceResponse::from)
                .toList();
    }

    // 주변 강좌 검색
    public List<MapPlaceResponse> findLecturesWithinRadius(
            double longitude, double latitude, double radius, List<LectureCategory> category) {

        return mongoPlaceRepository.findAllLectureByLocationNear(longitude, latitude, radius, category).stream()
                .map(MapPlaceResponse::from)
                .toList();
    }

    // 특정 조건 시설 검색
    public SearchPlaceResponseList searchFacilityPlaces(
            double longitude, double latitude, int maxDistant, List<FacilityCategory> categoryList, SortType sortType,
            String keyword, long page, long size) {

        List<SearchPlaceResponse> allSearchResult = placeRepository.findAllFacilityByLocationWithDistance(
                longitude, latitude, maxDistant, categoryList, sortType, keyword, page, size);

        return getSearchPlaceResponseList(allSearchResult, size, categoryList.stream()
                .map(Enum::name), page, longitude, latitude, maxDistant, keyword, sortType);
    }

    // 특정 조건 강좌 검색
    public SearchPlaceResponseList searchLecturePlaces(
            double longitude, double latitude, int maxDistant, List<LectureCategory> categoryList, SortType sortType,
            String keyword, long page, long size) {

        List<SearchPlaceResponse> allSearchResult = placeRepository.findAllLectureByLocationWithDistance(
                longitude, latitude, maxDistant, categoryList, sortType, keyword, page, size);

        // hasNext 계산: 현재 페이지의 데이터 수가 10개이고 전체 데이터 수가 page가 끝나는 지점보다 클 경우 false
        return getSearchPlaceResponseList(allSearchResult, size, categoryList.stream()
                .map(Enum::name), page, longitude, latitude, maxDistant, keyword, sortType);
    }

    private static SearchPlaceResponseList getSearchPlaceResponseList(
            List<SearchPlaceResponse> allSearchResult, long size, Stream<String> categoryList, long page,
            double longitude, double latitude, int maxDistant, String keyword, SortType sortType
    ) {
        // hasNext 계산: 현재 페이지의 데이터 수가 10개이고 전체 데이터 수가 page가 끝나는 지점보다 클 경우 false
        boolean hasNext = allSearchResult.size() == size + 1;

        // 마지막 원소를 제외한 서브 리스트 생성
        if (hasNext) {
            allSearchResult = allSearchResult.subList(0, allSearchResult.size() - 1);
        }

        List<String> strCategoryList = categoryList
                .toList();

        return SearchPlaceResponseList.of(page, hasNext, longitude, latitude, maxDistant, keyword, sortType,
                strCategoryList, allSearchResult);
    }

    // 특정 장소 상세 조회
    public PlaceDetailWithReviews findPlaceDetail(Long userId, Long placeId, Long size) {
        return placeRepository.findPlaceDetail(placeId, userId, size);
    }

    // 특정 장소 정보 조회
    public Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException(PlaceErrorCode.PLACE_NOT_FOUND));
    }
}
