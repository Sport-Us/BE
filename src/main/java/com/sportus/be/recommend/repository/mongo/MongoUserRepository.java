package com.sportus.be.recommend.repository.mongo;

import com.sportus.be.recommend.domain.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserRepository extends MongoRepository<MongoUser, String>, MongoUserRepositoryCustom {

}
