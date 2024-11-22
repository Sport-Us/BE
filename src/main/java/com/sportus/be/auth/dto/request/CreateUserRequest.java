package com.sportus.be.auth.dto.request;

import static com.sportus.be.user.domain.type.Provider.SELF;

import com.sportus.be.user.domain.User;
import com.sportus.be.user.domain.type.Gender;
import com.sportus.be.user.domain.type.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.security.crypto.password.PasswordEncoder;

public record CreateUserRequest(
        LocalDate birthDate,
        @Email(message = "이메일 형식을 맞춰주세요")
        String email,
        @Size(min = 8, message = "비밀번호를 8자 이상 입력해주세요")
        @NotBlank(message = "비밀번호를 입력해주세요")
        String password,
        String nickname,
        Gender gender
) {
    public User toUser(PasswordEncoder passwordEncoder, String imagePath) {
        return User.builder()
                .role(Role.ROLE_USER)
                .gender(gender)
                .birthDate(birthDate)
                .email(email)
                .password(passwordEncoder.encode(password))
                .provider(SELF)
                .nickname(nickname)
                .profileImageUrl(imagePath)
                .isOnboarded(false)
                .build();
    }
}