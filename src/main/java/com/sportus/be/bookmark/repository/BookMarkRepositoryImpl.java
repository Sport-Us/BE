package com.sportus.be.bookmark.repository;

import static com.sportus.be.bookmark.domain.QBookMark.bookMark;
import static com.sportus.be.place.domain.QPlace.place;
import static com.sportus.be.review.domain.QReview.review;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sportus.be.bookmark.dto.response.BookMarkPlaceResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookMarkPlaceResponse> getBookMarkList(Long userId, Long lastBookMarkId, Long size) {
        return queryFactory.select(
                        Projections.constructor(BookMarkPlaceResponse.class,
                                bookMark.id,              // bookmarkId
                                place.id,                 // placeId
                                place.name,               // name
                                place.address,            // address
                                place.facilityCategory,           // category
                                place.lectureCategory,
                                review.rating.avg().coalesce(0.0), // averageRating
                                review.id.count().coalesce(0L)      // reviewCount
                        )
                )
                .from(bookMark)                    // 북마크 테이블에서 시작
                .join(bookMark.place, place)      // 북마크와 장소 조인
                .leftJoin(place.reviewList, review) // 장소와 리뷰를 왼쪽 조인
                .where(bookMark.user.id.eq(userId), // 사용자 ID 필터
                        bookMark.id.lt(lastBookMarkId)) // 마지막 북마크 ID 필터
                .groupBy(bookMark.id) // 그룹화 추가
                .orderBy(bookMark.id.desc())         // 내림차순 정렬
                .limit(size)                          // 제한
                .fetch();
    }



    @Override
    public Boolean hasNext(Long userId, Long lastBookMarkId, Long size) {
        return queryFactory.selectOne()
                .from(bookMark)
                .where(bookMark.user.id.eq(userId), bookMark.id.lt(lastBookMarkId - size))
                .fetchFirst() != null;
    }
}