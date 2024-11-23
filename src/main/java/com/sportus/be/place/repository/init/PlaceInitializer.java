package com.sportus.be.place.repository.init;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sportus.be.global.util.DummyDataInit;
import com.sportus.be.place.domain.Place;
import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import com.sportus.be.place.repository.PlaceRepository;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class PlaceInitializer implements ApplicationRunner {

    private final PlaceRepository placeRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (placeRepository.count() > 0) {
            log.info("[Place]더미 데이터 존재");
        } else {
            importCsvToPlace("data/개방학교체육시설.csv", 8, 14, 15, FacilityCategory.SCHOOL, 5, 10, 2, -1);
            importCsvToPlace("data/공공체육시설프로그램정보.csv", 7, 19, 20, null, 1, 18, 4, 8);
            importCsvToPlace("data/내주변문화지도연계장애인스포츠강좌이용권시설정보.csv", 6, 8, 9, FacilityCategory.DISABLED, 0,
                    Integer.MAX_VALUE, 2, -1);
            importCsvToPlace("data/스포츠강좌이용권시설데이터.csv", 8, 12, 13, FacilityCategory.PRIVATE, 4, Integer.MAX_VALUE, 1,
                    -1);
            importCsvToPlace("data/스포츠강좌이용권이용현황정보.csv", 5, 9, 10, null, 1, 8, 3, 2);
            importCsvToPlace("data/장애인스포츠강좌이용권시설정보.csv", 7, 11, 12, FacilityCategory.DISABLED, 4, 13, 1, -1);
            importCsvToPlace("data/장애인스포츠강좌이용권이용현황.csv", 8, 18, 19, null, 4, 13, 1, 3);
            importCsvToPlace("data/전국공공체육시설데이터.csv", 23, 24, 25, FacilityCategory.PUBLIC, 0, 6, 10, -1);
            importCsvToPlace("data/전국체육시설현황.csv", 7, 8, 9, FacilityCategory.PRIVATE, 0, 3, 11, -1);
            importCsvToPlace("data/청소년유아동이용가능체육시설프로그램정보.csv", 7, 24, 25, null, 1, 19, 4, 9);
        }
    }

    private void importCsvToPlace(String filePath, int addressIdx, int longitudeIdx, int latitudeIdx,
                                  FacilityCategory facilityCategory, int nameIdx, int detailInfoIdx, int regionIdx,
                                  int lectureCategoryIdx) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        ) {
            CSVReader csvReader = new CSVReader(reader);
            String[] nextLine;
            // 첫 번째 줄은 헤더이므로 건너뜁니다.
            csvReader.readNext();

            while ((nextLine = csvReader.readNext()) != null) {
                String address = nextLine[addressIdx]; // 도로명주소
                double latitude = Double.parseDouble(nextLine[latitudeIdx]); // 위도
                double longitude = Double.parseDouble(nextLine[longitudeIdx]); // 경도
                String name = nextLine[nameIdx];
                String region = nextLine[regionIdx];

                String detailInfo = detailInfoIdx != Integer.MAX_VALUE ? nextLine[detailInfoIdx] : "";

                LectureCategory lectureCategory = null;
                if (facilityCategory == null) {
                    lectureCategory = LectureCategory.findByName(nextLine[lectureCategoryIdx]);
                }

                Place place = Place.builder()
                        .name(name)
                        .address(address)
                        .location(createPoint(latitude, longitude))
                        .facilityCategory(facilityCategory)
                        .lectureCategory(lectureCategory)
                        .detailInfo(detailInfo)
                        .region(region)
                        .build();

                placeRepository.save(place);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private Point createPoint(double latitude, double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(4326); // SRID를 4326으로 설정
        return point;
    }
}
