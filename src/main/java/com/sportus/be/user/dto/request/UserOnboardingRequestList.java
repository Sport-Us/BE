package com.sportus.be.user.dto.request;

import java.util.List;

public record UserOnboardingRequestList(
        List<UserOnboardingRequest> userOnboardingRequestList
) {
}
