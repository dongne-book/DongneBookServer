package com.dongnaebook.init;

import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }

        // 일반 사용자들
        User user1 = User.builder()
                .email("jyp@example.com")
                .nickname("지영이")
                .password(passwordEncoder.encode("pwd"))
                .adminLevel(1)
                .build();

        User user2 = User.builder()
                .email("hsk@example.com")
                .nickname("효식이")
                .password(passwordEncoder.encode("pwd"))
                .adminLevel(1)
                .build();

        User user3 = User.builder()
                .email("syl@example.com")
                .nickname("서연이")
                .password(passwordEncoder.encode("pwd"))
                .adminLevel(1)
                .build();

        User user4 = User.builder()
                .email("sjj@example.com")
                .nickname("상진이")
                .password(passwordEncoder.encode("pwd"))
                .adminLevel(1)
                .build();

        User user5 = User.builder()
                .email("jhk@example.com")
                .nickname("지현이")
                .password(passwordEncoder.encode("pwd"))
                .adminLevel(1)
                .build();

        User admin = User.builder()
                .email("admin@admin.com")
                .nickname("관리자")
                .password(passwordEncoder.encode("pwd"))
                .adminLevel(2)
                .build();

        // 모든 사용자 저장
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(admin);
    }
}