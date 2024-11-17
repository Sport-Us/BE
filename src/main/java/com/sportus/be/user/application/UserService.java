package com.sportus.be.user.application;

import static com.sportus.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.sportus.be.recommend.repository.mongo.MongoUserRepository;
import com.sportus.be.user.domain.User;
import com.sportus.be.user.dto.request.UserOnboardingRequestList;
import com.sportus.be.user.dto.response.MypageResponse;
import com.sportus.be.user.exception.UserNotFoundException;
import com.sportus.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final MongoUserRepository mongoUserRepository;

    @Transactional
    public void onboarding(Long userId, UserOnboardingRequestList userOnboardingRequestList) {
        User user = getUserById(userId);
        user.onboarding(userOnboardingRequestList.userOnboardingRequestList());

        makeMongoUser(user, userOnboardingRequestList);
    }

    private void makeMongoUser(User user, UserOnboardingRequestList userOnboardingRequestList) {
        mongoUserRepository.save(userOnboardingRequestList.toMongoUser(user));
    }

    // 마이페이지 유저 정보 조회
    public MypageResponse getMypage(Long userId) {
        User user = getUserById(userId);
        return MypageResponse.from(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}