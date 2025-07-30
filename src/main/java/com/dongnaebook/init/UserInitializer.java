package com.dongnaebook.init;

import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import com.dongnaebook.domain.user.vo.Email;
import com.dongnaebook.domain.user.vo.Nickname;
import com.dongnaebook.domain.user.vo.Password;
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
                .email(new Email("jyp@example.com"))
                .nickname(new Nickname("지영이"))
                .password(new Password(passwordEncoder.encode("pwd")))
                .adminLevel(1)
                .build();

        User user2 = User.builder()
                .email(new Email("hsk@example.com"))
                .nickname(new Nickname("효식이"))
                .password(new Password(passwordEncoder.encode("pwd")))
                .adminLevel(1)
                .build();

        User user3 = User.builder()
                .email(new Email("syl@example.com"))
                .nickname(new Nickname("서연이"))
                .password(new Password(passwordEncoder.encode("pwd")))
                .adminLevel(1)
                .build();

        User user4 = User.builder()
                .email(new Email("sjj@example.com"))
                .nickname(new Nickname("상진이"))
                .password(new Password(passwordEncoder.encode("pwd")))
                .adminLevel(1)
                .build();

        User user5 = User.builder()
                .email(new Email("jhk@example.com"))
                .nickname(new Nickname("지현이"))
                .password(new Password(passwordEncoder.encode("pwd")))
                .adminLevel(1)
                .build();

        User admin = User.builder()
                .email(new Email("admin@admin.com"))
                .nickname(new Nickname("관리자"))
                .password(new Password(passwordEncoder.encode("pwd")))
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