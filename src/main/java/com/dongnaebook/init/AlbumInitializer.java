package com.dongnaebook.init;

import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.album.AlbumRepository;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class AlbumInitializer implements ApplicationRunner {

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (albumRepository.count() > 0) {
            return; // 이미 데이터가 있으면 스킵
        }

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return; // 사용자가 없으면 스킵
        }

        // 지영이의 앨범들
        createAlbum(users.get(0), "서울 데이트 코스",
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 7));

        createAlbum(users.get(0), "카페 탐방 일기",
                LocalDate.of(2024, 2, 10), LocalDate.of(2024, 2, 14));

        createAlbum(users.get(0), "인천 나들이",
                LocalDate.of(2024, 3, 15), LocalDate.of(2024, 3, 20));

        // 효식이의 앨범들
        createAlbum(users.get(1), "부산 바다 여행",
                LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 5));

        createAlbum(users.get(1), "서울 야경 투어",
                LocalDate.of(2024, 5, 10), LocalDate.of(2024, 5, 12));

        createAlbum(users.get(1), "전국 맛집 탐방",
                LocalDate.of(2024, 6, 20), LocalDate.of(2024, 6, 25));

        // 서연이의 앨범들
        createAlbum(users.get(2), "예술 마을 탐방",
                LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 3));

        createAlbum(users.get(2), "광주 문화 여행",
                LocalDate.of(2024, 8, 15), LocalDate.of(2024, 8, 17));

        createAlbum(users.get(2), "역사 유적지 투어",
                LocalDate.of(2024, 9, 5), LocalDate.of(2024, 9, 8));

        // 상진이의 앨범들
        createAlbum(users.get(3), "대구 도심 탐방",
                LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 3));

        createAlbum(users.get(3), "대전 과학 여행",
                LocalDate.of(2024, 11, 10), LocalDate.of(2024, 11, 12));

        createAlbum(users.get(3), "쇼핑몰 투어",
                LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));

        // 지현이의 앨범들
        createAlbum(users.get(4), "서울 관광지 투어",
                LocalDate.of(2024, 1, 20), LocalDate.of(2024, 1, 25));

        createAlbum(users.get(4), "남산 데이트",
                LocalDate.of(2024, 2, 14), LocalDate.of(2024, 2, 14));

        createAlbum(users.get(4), "KTX 전국 여행",
                LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 10));

        SecurityContextHolder.clearContext();
    }

    private void createAlbum(User user, String name, LocalDate startDate, LocalDate endDate) {
        setSecurityContext(user.getEmail());

        Album album = Album.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        albumRepository.save(album);
    }

    private void setSecurityContext(String email) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(email, null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}