package com.sportus.be.bookmark.repository;

import com.sportus.be.bookmark.domain.BookMark;
import com.sportus.be.place.domain.Place;
import com.sportus.be.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>, BookMarkRepositoryCustom {

    Optional<BookMark> findByUserAndPlace(User user, Place place);

    @Query("SELECT COALESCE(MAX(id), 0) AS lastBookMarkId "
            + "FROM BookMark "
            + "WHERE user.id = :userId")
    Long findLastBookMarkByUserId(Long userId);
}
