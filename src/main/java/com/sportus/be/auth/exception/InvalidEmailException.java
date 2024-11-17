package com.sportus.be.auth.exception;

import com.sportus.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidEmailException extends RuntimeException {
    private final ErrorCode errorCode;
}