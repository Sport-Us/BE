package com.sportus.be.place.repository.init;

import com.sportus.be.global.util.DummyDataInit;
import com.sportus.be.place.domain.MongoPlace;
import com.sportus.be.place.repository.PlaceRepository;
import com.sportus.be.place.repository.mongo.MongoPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@DummyDataInit
public class MongoPlaceInitializer implements ApplicationRunner {

    private final PlaceRepository placeRepository;
    private final MongoPlaceRepository mongoPlaceRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (mongoPlaceRepository.count() > 0) {
            log.info("[MongoPlace]더미 데이터 존재");
        } else {
            placeRepository.findAll().forEach(place -> {
                MongoPlace mongoPlace = MongoPlace.builder()
                        .lectureCategory(place.getLectureCategory())
                        .facilityCategory(place.getFacilityCategory())
                        .address(place.getAddress())
                        .location(new GeoJsonPoint(place.getLocation().getX(), place.getLocation().getY()))
                        .placeId(place.getId())
                        .build();

                mongoPlaceRepository.save(mongoPlace);
            });
        }
    }
}
