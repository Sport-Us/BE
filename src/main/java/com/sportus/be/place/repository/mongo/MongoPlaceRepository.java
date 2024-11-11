package com.sportus.be.place.repository.mongo;

import com.sportus.be.place.domain.MongoPlace;
import com.sportus.be.place.domain.type.LectureCategory;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoPlaceRepository extends MongoRepository<MongoPlace, String> {

    @Query("{ location: { $near: { $geometry: { type: 'Point', coordinates: [?0, ?1] }, $maxDistance: ?2 } }, category: { $in: ?3 } }")
    List<MongoPlace> findAllLectureByLocationNear(double longitude, double latitude, double maxDistance, List<LectureCategory> categories);
}
