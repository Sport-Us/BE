package com.sportus.be.auth.application;

import static com.sportus.be.auth.exception.errorcode.AuthErrorCode.INVALID_EMAIL;
import static com.sportus.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.sportus.be.auth.dto.request.CreateUserRequest;
import com.sportus.be.auth.dto.request.SignInRequest;
import com.sportus.be.auth.dto.response.ReissueTokenResponse;
import com.sportus.be.auth.dto.response.SelfLoginResponse;
import com.sportus.be.auth.exception.InvalidEmailException;
import com.sportus.be.auth.exception.TokenNotValidException;
import com.sportus.be.auth.exception.errorcode.AuthErrorCode;
import com.sportus.be.auth.util.JwtTokenProvider;
import com.sportus.be.global.application.AWSStorageService;
import com.sportus.be.user.domain.User;
import com.sportus.be.user.domain.type.Provider;
import com.sportus.be.user.exception.UserNotFoundException;
import com.sportus.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AWSStorageService awsStorageService;
    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private static final String DUMMY_PROFILE_IMAGE_URL = "/profile/ic_profile.svg";

    public String getTestToken(Long userId) {
        return jwtTokenProvider.createAccessToken(userRepository.findById(userId).orElseThrow());
    }

    public void signUp(CreateUserRequest request, MultipartFile file) {
        if (!userRepository.existsByEmailAndProvider(request.email(), Provider.SELF)) {
            String imagePath = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com" + DUMMY_PROFILE_IMAGE_URL;

            if (file != null) {
                imagePath = awsStorageService.uploadFile(file, "profile");
            }

            User user = request.toUser(passwordEncoder, imagePath);
            userRepository.save(user);
        } else {
            throw new InvalidEmailException(INVALID_EMAIL);
        }
    }

    public boolean validateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public SelfLoginResponse signIn(
            SignInRequest signUpRequest
    ) {
        return loginService.login(signUpRequest.email(), signUpRequest.password());
    }

    public ReissueTokenResponse reIssueToken(String refreshToken) {

        refreshToken = resolveToken(refreshToken);

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new TokenNotValidException(AuthErrorCode.TOKEN_NOT_VALID);
        }

        User user = jwtTokenProvider.getUser(refreshToken);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user);

        return ReissueTokenResponse.from(accessToken, newRefreshToken);
    }

    private String resolveToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        throw new TokenNotValidException(AuthErrorCode.TOKEN_NOT_VALID);
    }

    @Transactional
    public void withdraw(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        () -> {
                            throw new UserNotFoundException(USER_NOT_FOUND);
                        });
    }
}