package com.sportus.be.place.presentation;

import com.sportus.be.global.dto.ResponseTemplate;
import com.sportus.be.place.application.PlaceService;
import com.sportus.be.place.application.type.SortType;
import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import com.sportus.be.place.dto.response.MapPlaceResponseList;
import com.sportus.be.place.dto.response.SearchPlaceResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Place", description = "지도, 장소 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "주변 시설 검색", description = "경도, 위도, 거리를 기반으로 검색, 거리는 m 단위로 입력<br>" +
            "예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116<br>" +
            "카테고리: PUBLIC(공공 기관), PRIVATE(민간 업체), DISABLED(장애인 가능 업체), SCHOOL(학교)")
    @GetMapping("/nearby/facilities")
    public ResponseEntity<ResponseTemplate<?>> getNearbyFacilityPlaces(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double radius,
            @RequestParam List<FacilityCategory> category) {

        MapPlaceResponseList placesWithinRadius =
                MapPlaceResponseList.from(
                        placeService.findFacilitiesWithinRadius(longitude, latitude, radius, category));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(placesWithinRadius));
    }

    @Operation(summary = "주변 강좌 검색", description = "경도, 위도, 거리를 기반으로 검색, 거리는 m 단위로 입력<br>" +
            "예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116" +
            "카테고리: 너무 많아서 생략(스웨거 및 피그마 참고)")
    @GetMapping("nearby/lectures")
    public ResponseEntity<ResponseTemplate<?>> getNearbyLecturePlaces(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double radius,
            @RequestParam List<LectureCategory> category) {

        MapPlaceResponseList placesWithinRadius =
                MapPlaceResponseList.from(placeService.findLecturesWithinRadius(longitude, latitude, radius, category));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(placesWithinRadius));
    }

    @Operation(summary = "장소 검색", description =
            "경도, 위도, 카테고리, 정렬 기준을 기반으로 검색, page는 0부터 시작, hasNext가 false이면 다음 데이터 X, ALL 주면 모든 카테고리 조회<br>" +
                    "예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116")
    @GetMapping("/search/facilities")
    public ResponseEntity<ResponseTemplate<?>> searchFacilities(
            @RequestParam Double longitude,
            @RequestParam Double latitude,
            @RequestParam Integer maxDistance,
            @RequestParam List<FacilityCategory> category,
            @RequestParam SortType sortType,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        SearchPlaceResponseList nearestPlaces =
                placeService.searchFacilityPlaces(longitude, latitude, maxDistance, category, sortType, keyword,
                        page, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(nearestPlaces));
    }

    @Operation(summary = "강좌 검색", description =
            "경도, 위도, 카테고리, 정렬 기준을 기반으로 검색, page는 0부터 시작, hasNext가 false이면 다음 데이터 X, ALL 주면 모든 카테고리 조회<br>" +
                    "예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116")
    @GetMapping("/search/lectures")
    public ResponseEntity<ResponseTemplate<?>> searchLectures(
            @RequestParam Double longitude,
            @RequestParam Double latitude,
            @RequestParam Integer maxDistance,
            @RequestParam List<LectureCategory> category,
            @RequestParam SortType sortType,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        SearchPlaceResponseList nearestPlaces =
                placeService.searchLecturePlaces(longitude, latitude, maxDistance, category, sortType, keyword,
                        page, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(nearestPlaces));
    }


    @Operation(summary = "장소 상세 조회", description = "장소 상세 정보와 리뷰를 조회 - 최신순<br>" +
            "이 API를 통해 리뷰를 확인할 수 있음 - 해당 api 다음에 /reviews/{placeId} API를 호출해야함 - 마지막으로 나온 reviewId를 다음 API에 넣어야함, 8475번 place에 더미 데이터 존재")
    @GetMapping("/detail")
    public ResponseEntity<ResponseTemplate<?>> getPlaceDetail(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long placeId,
            @RequestParam(defaultValue = "5") Long size
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(placeService.findPlaceDetail(userId, placeId, size)));
    }
}
