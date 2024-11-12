package com.sportus.be.user.application;

import static com.sportus.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.sportus.be.user.domain.User;
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

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

}