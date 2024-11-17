package com.sportus.be.global.exception.handler;

import com.sportus.be.auth.exception.InvalidEmailException;
import com.sportus.be.auth.exception.TokenNotValidException;
import com.sportus.be.cardnews.exception.CardNewsNotFoundException;
import com.sportus.be.global.exception.FileConvertFailException;
import com.sportus.be.global.exception.errorcode.ErrorCode;
import com.sportus.be.global.exception.errorcode.GlobalErrorCode;
import com.sportus.be.global.exception.response.ErrorResponse;
import com.sportus.be.global.exception.response.ErrorResponse.ValidationError;
import com.sportus.be.global.exception.response.ErrorResponse.ValidationErrors;
import com.sportus.be.place.exception.PlaceNotFoundException;
import com.sportus.be.recommend.exception.MongoUserNotFoundException;
import com.sportus.be.review.exception.CanNotDeleteReviewException;
import com.sportus.be.review.exception.ReviewNotFoundException;
import com.sportus.be.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger("ErrorLogger");
    private static final String LOG_FORMAT_INFO = "\n[🔵INFO] - ({} {})\n(id: {}, role: {})\n{}\n {}: {}";
    private static final String LOG_FORMAT_ERROR = "\n[🔴ERROR] - ({} {})\n(id: {}, role: {})";

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<Object> handleTokenNotValid(TokenNotValidException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleInvalidEmail(InvalidEmailException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(MongoUserNotFoundException.class)
    public ResponseEntity<Object> handleMongoUserNotFound(MongoUserNotFoundException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(CardNewsNotFoundException.class)
    public ResponseEntity<Object> handleCardNewsNotFound(CardNewsNotFoundException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<Object> handlePlaceNotFound(PlaceNotFoundException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(CanNotDeleteReviewException.class)
    public ResponseEntity<Object> handleCanNotDeleteReview(CanNotDeleteReviewException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<Object> handleReviewNotFound(ReviewNotFoundException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(FileConvertFailException.class)
    public ResponseEntity<Object> handleFileConvertFail(FileConvertFailException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        return handleExceptionInternal(e.getErrorCode());
    }

    /**
     * DTO @Valid 관련 exception 처리
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException e,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return handleExceptionInternal(e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        logInfo(GlobalErrorCode.INVALID_PARAMETER, e, request);
        return handleExceptionInternal(GlobalErrorCode.INVALID_PARAMETER);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e, HttpServletRequest request) {
        logError(e, request);
        return handleExceptionInternal(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .results(new ValidationErrors(null))
                .build();
    }

    // DTO @Valid 관련 exception 처리
    private ResponseEntity<Object> handleExceptionInternal(BindException e) {
        return ResponseEntity.status(GlobalErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(makeErrorResponse(e));
    }

    private ErrorResponse makeErrorResponse(BindException e) {
        final List<ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::from)
                .toList();

        return ErrorResponse.builder()
                .isSuccess(false)
                .code(GlobalErrorCode.INVALID_PARAMETER.name())
                .message(GlobalErrorCode.INVALID_PARAMETER.getMessage())
                .results(new ValidationErrors(validationErrorList))
                .build();
    }

    private void logInfo(ErrorCode ec, Exception e, HttpServletRequest request) {
        log.info(LOG_FORMAT_INFO, request.getMethod(), request.getRequestURI(), getUserId(),
                getRole(), ec.getHttpStatus(), e.getClass().getName(), e.getMessage());
    }

    private void logError(Exception e, HttpServletRequest request) {
        log.error(LOG_FORMAT_ERROR, request.getMethod(), request.getRequestURI(), getUserId(), getRole(), e);
    }

    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // 사용자의 id
        } else {
            return "anonymous";
        }
    }

    private String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().toString(); // 사용자의 role
        } else {
            return "anonymous";
        }
    }
}
