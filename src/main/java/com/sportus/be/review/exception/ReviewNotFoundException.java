package com.sportus.be.review.exception;

import com.sportus.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}
