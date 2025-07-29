package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.vo.Email;
import com.dongnaebook.domain.user.vo.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(Email email);
    Optional<User> findByEmail(Email email);
    Optional<User> findByKakaoId(String kakaoId);
    Optional<User> findByGoogleId(String googleId);

    Optional<User> findByNickname(Nickname nickname);
}
