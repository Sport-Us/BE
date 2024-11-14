package com.sportus.be.user.domain;

import com.sportus.be.user.domain.type.OnboardingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "onboarding")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Onboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "onboarding_type", nullable = false)
    OnboardingType onboardingType;

    @Column(name = "content", nullable = false)
    String content;

    @Builder
    public Onboarding(User user, OnboardingType onboardingType, String content) {
        this.user = user;
        this.onboardingType = onboardingType;
        this.content = content;
    }
}