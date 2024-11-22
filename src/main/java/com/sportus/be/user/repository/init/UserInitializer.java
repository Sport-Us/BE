package com.sportus.be.user.repository.init;

import com.sportus.be.global.util.DummyDataInit;
import com.sportus.be.user.domain.User;
import com.sportus.be.user.domain.type.Gender;
import com.sportus.be.user.domain.type.Provider;
import com.sportus.be.user.domain.type.Role;
import com.sportus.be.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class UserInitializer implements ApplicationRunner {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DUMMY_PROFILE_IMAGE_URL = "/profile/ic_profile.svg";

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {
            for (int i = 0; i < 10; i++) {
                List<User> userList = new ArrayList<>();

                User DUMMY_ADMIN = User.builder()
                        .nickname("관리자")
                        .profileImageUrl(
                                "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com" + DUMMY_PROFILE_IMAGE_URL)
                        .email("admin@naver.com")
                        .password(passwordEncoder.encode("adminPassword"))
                        .provider(Provider.SELF)
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .gender(Gender.MALE)
                        .role(Role.USER)
                        .isOnboarded(true)
                        .build();

                User DUMMY_USER1 = User.builder()
                        .nickname("user1")
                        .profileImageUrl(
                                "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com" + DUMMY_PROFILE_IMAGE_URL)
                        .email("user1@naver.com")
                        .password(passwordEncoder.encode("user1Password"))
                        .provider(Provider.SELF)
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .gender(Gender.MALE)
                        .role(Role.USER)
                        .isOnboarded(true)
                        .build();

                User DUMMY_USER2 = User.builder()
                        .nickname("user2")
                        .profileImageUrl(
                                "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com" + DUMMY_PROFILE_IMAGE_URL)
                        .email("user2@naver.com")
                        .password(passwordEncoder.encode("user2Password"))
                        .provider(Provider.SELF)
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .gender(Gender.MALE)
                        .role(Role.USER)
                        .isOnboarded(true)
                        .build();

                userList.add(DUMMY_ADMIN);
                userList.add(DUMMY_USER1);
                userList.add(DUMMY_USER2);

                userRepository.saveAll(userList);
            }
        }
    }
}
