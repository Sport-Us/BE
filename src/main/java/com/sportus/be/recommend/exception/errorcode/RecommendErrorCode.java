package com.sportus.be.recommend.exception.errorcode;

import com.sportus.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecommendErrorCode implements ErrorCode {

    MONGO_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "MongoUser not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}