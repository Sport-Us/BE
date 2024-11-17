package com.sportus.be.user.domain;

import com.sportus.be.user.domain.type.Gender;
import com.sportus.be.user.domain.type.Provider;
import com.sportus.be.user.domain.type.Role;
import com.sportus.be.user.dto.request.UserOnboardingRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "is_onboarded", nullable = false)
    private Boolean isOnboarded;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Onboarding> onboardingList;

    @Builder
    public User(String nickname, String profileImageUrl, String email, String password, Provider provider,
                String socialId, LocalDate birthDate, Gender gender, Boolean isOnboarded, Role role) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.socialId = socialId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.isOnboarded = isOnboarded;
        this.role = role;
    }

    public void onboarding(List<UserOnboardingRequest> userOnboardingRequestList) {
        userOnboardingRequestList.forEach(userOnboardingRequest -> {
            Onboarding onboarding = userOnboardingRequest.toEntity(this);
            this.onboardingList.add(onboarding);
            this.isOnboarded = true;
        });
    }
}