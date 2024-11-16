package com.sportus.be.recommend.exception;

import com.sportus.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MongoUserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}
