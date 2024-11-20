package com.sportus.be.auth.presentation;

import static com.sportus.be.global.dto.ResponseTemplate.EMPTY_RESPONSE;

import com.sportus.be.auth.application.AuthService;
import com.sportus.be.auth.dto.request.CreateUserRequest;
import com.sportus.be.auth.dto.request.SignInRequest;
import com.sportus.be.global.dto.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Auth", description = "Auth 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "테스트용 토큰발급", description = "테스트용 토큰발급")
    @GetMapping("/test/{userId}")
    public String testToken(@PathVariable Long userId) {
        return authService.getTestToken(userId);
    }

    @Operation(summary = "자체 회원가입", description = "소셜 로그인 없는 자체 회원가입")
    @PostMapping(value = "/sign-up", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseTemplate<Object>> signUp(
            @Valid @RequestPart CreateUserRequest request,
            @RequestPart(required = false) MultipartFile file) {

        authService.signUp(request, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "로그인 진행", description = "access token은 header에 Authorization: Bearer 로, refresh token은 쿠키로 전달")
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseTemplate<?>> signIn(
            @RequestBody SignInRequest signInRequest, HttpServletResponse response) {

        authService.signIn(signInRequest, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "닉네임 중복 검사", description = "닉네임이 존재하면 true, 존재하지 않으면 false를 반환합니다")
    @GetMapping("/nickname/validation")
    public ResponseEntity<ResponseTemplate<Object>> validateNickname(
            @Valid @RequestParam String nickname) {

        boolean response = authService.validateNickname(nickname);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "access token 재발급", description = "access token 재발급 및 refresh token 갱신")
    @GetMapping("/re-issue")
    public ResponseEntity<ResponseTemplate<?>> reIssueToken(
            @CookieValue(name = "REFRESH_TOKEN") String refreshToken, HttpServletResponse response) {

        authService.reIssueToken(refreshToken, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "회원탈퇴", description = "회원탈퇴")
    @DeleteMapping("/withdraw")
    public ResponseEntity<ResponseTemplate<Object>> withdraw(
            @AuthenticationPrincipal Long userId) {

        authService.withdraw(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}
