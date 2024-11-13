package com.sportus.be.review.application;

import com.amazonaws.util.StringUtils;
import com.sportus.be.global.application.AWSStorageService;
import com.sportus.be.place.application.PlaceService;
import com.sportus.be.place.domain.Place;
import com.sportus.be.review.dto.request.ReviewUploadRequest;
import com.sportus.be.review.dto.response.ReviewSimpleResponse;
import com.sportus.be.review.dto.response.ReviewSimpleResponseList;
import com.sportus.be.review.exception.CanNotDeleteReviewException;
import com.sportus.be.review.exception.ReviewNotFoundException;
import com.sportus.be.review.exception.errorcode.ReviewErrorCode;
import com.sportus.be.review.repository.ReviewRepository;
import com.sportus.be.user.application.UserService;
import com.sportus.be.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final PlaceService placeService;
    private final AWSStorageService awsStorageService;

    public ReviewSimpleResponseList getReviewSimpleResponse(Long placeId, Long lastReviewId, Long size) {

        List<ReviewSimpleResponse> reviewSimpleResponseList =
                reviewRepository.getReviewSimpleResponse(placeId, lastReviewId, size);

        boolean hasNext = reviewSimpleResponseList.size() == size + 1;

        // 마지막 원소를 제외한 서브 리스트 생성
        if (hasNext) {
            reviewSimpleResponseList = reviewSimpleResponseList.subList(0, reviewSimpleResponseList.size() - 1);
        }

        return ReviewSimpleResponseList.of(hasNext, reviewSimpleResponseList);
    }

    public ReviewSimpleResponseList getMyReview(Long userId, Long lastReviewId, Long size) {

        List<ReviewSimpleResponse> reviewSimpleResponse = (lastReviewId == 0)
                ? reviewRepository.getMyFirstReview(userId) : reviewRepository.getMyReview(userId, lastReviewId, size);

        Boolean hasNext = reviewRepository.hasNextMyReview(userId, lastReviewId, size);

        return ReviewSimpleResponseList.of(hasNext, reviewSimpleResponse);
    }

    @Transactional
    public void uploadReview(Long userId, ReviewUploadRequest reviewUploadRequest, MultipartFile file) {

        String imageUrl = "";
        if (file != null) {
            imageUrl = awsStorageService.uploadFile(file, "reviews");
        }

        User user = userService.getUserById(userId);
        Place place = placeService.getPlaceById(reviewUploadRequest.placeId());

        reviewRepository.save(reviewUploadRequest.toEntity(place, user, imageUrl));
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        reviewRepository.findById(reviewId)
                .ifPresentOrElse(
                        review -> {
                            if (review.getUser().getId().equals(userId)) {
                                if (StringUtils.hasValue(review.getReviewImageUrl())) {
                                    awsStorageService.deleteFile(review.getReviewImageUrl());
                                }
                                reviewRepository.delete(review);
                            } else {
                                throw new CanNotDeleteReviewException(ReviewErrorCode.CANNOT_DELETE_REVIEW);
                            }
                        },
                        () -> {
                            throw new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND);
                        }
                );
    }
}
