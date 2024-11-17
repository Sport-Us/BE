package com.sportus.be.auth.exception.errorcode;

import com.sportus.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    TOKEN_NOT_VALID(HttpStatus.FORBIDDEN, "Refresh Token is Not Valid."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Invalid Password"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "Already exist Email"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
