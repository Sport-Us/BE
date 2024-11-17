package com.sportus.be.user.repository;

import com.sportus.be.user.domain.User;
import com.sportus.be.user.domain.type.Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.provider = :provider AND u.socialId = :socialId")
    Optional<User> findByProviderAndSocialId(Provider provider, String socialId);

    Optional<User> findByEmail(String email);

    Boolean existsByEmailAndProvider(String email, Provider provider);

    Boolean existsByNickname(String nickname);
}
