package com.dongnaebook.init;

import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.album.AlbumRepository;
import com.dongnaebook.domain.post.Post;
import com.dongnaebook.domain.post.PostRepository;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Order(4)
public class PostInitializer implements ApplicationRunner {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final AlbumRepository albumRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (postRepository.count() > 0) {
            return; // 이미 데이터가 있으면 스킵
        }

        List<User> users = userRepository.findAll();
        List<Place> places = placeRepository.findAll();
        List<Album> albums = albumRepository.findAll();

        if (users.isEmpty() || places.isEmpty() || albums.isEmpty()) {
            return; // 필수 데이터가 없으면 스킵
        }

        Random random = new Random();

        // 지영이의 포스트들
        createPost(users.get(0), albums.get(0), places.get(0),
                "강남 스타벅스에서 친구들과", "새로 생긴 리저브 매장에서 특별한 커피를 마셨어요!",
                LocalDate.now().minusDays(3), true);

        createPost(users.get(0), albums.get(0), places.get(2),
                "경복궁 한복 체험", "전통 한복을 입고 궁궐을 구경하니 조선시대로 온 기분이에요.",
                LocalDate.now().minusDays(7), true);

        createPost(users.get(0), albums.get(0), places.get(17),
                "송도 센트럴파크 산책", "인공 운하를 따라 걸으며 도심 속 자연을 만끽했어요.",
                LocalDate.now().minusDays(12), true);

        // 효식이의 포스트들
        createPost(users.get(1), albums.get(1), places.get(1),
                "롯데월드타워 스카이서울", "118층에서 내려다본 서울 야경이 정말 장관이었습니다!",
                LocalDate.now().minusDays(5), true);

        createPost(users.get(1), albums.get(1), places.get(5),
                "해운대 일출 감상", "새벽 5시에 일어나서 본 일출이 정말 감동적이었어요.",
                LocalDate.now().minusDays(10), true);

        createPost(users.get(1), albums.get(1), places.get(9),
                "광안대교 드라이브", "야경이 아름다운 광안대교를 드라이브하며 부산의 밤을 만끽했습니다.",
                LocalDate.now().minusDays(15), false);

        // 서연이의 포스트들
        createPost(users.get(2), albums.get(2), places.get(6),
                "감천문화마을 예술 탐방", "벽화와 조형물들이 가득한 예술 마을을 구경했어요!",
                LocalDate.now().minusDays(8), true);

        createPost(users.get(2), albums.get(2), places.get(13),
                "양림동 펭귄마을", "역사와 문화가 살아있는 근대문화의 거리를 걸었습니다.",
                LocalDate.now().minusDays(18), true);

        createPost(users.get(2), albums.get(2), places.get(18),
                "인천 차이나타운 맛집투어", "원조 짜장면부터 월병까지 중국 음식을 실컷 먹었어요!",
                LocalDate.now().minusDays(25), true);

        // 상진이의 포스트들
        createPost(users.get(3), albums.get(3), places.get(10),
                "동성로 쇼핑몰 탐방", "대구 최고의 쇼핑 거리에서 하루 종일 쇼핑했습니다.",
                LocalDate.now().minusDays(14), true);

        createPost(users.get(3), albums.get(3), places.get(11),
                "83타워 전망대", "대구 시내가 한눈에 보이는 최고의 뷰포인트!",
                LocalDate.now().minusDays(20), true);

        createPost(users.get(3), albums.get(3), places.get(15),
                "엑스포과학공원 체험", "과학 체험관에서 신기한 실험들을 직접 해볼 수 있었어요.",
                LocalDate.now().minusDays(28), false);

        // 지현이의 포스트들
        createPost(users.get(4), albums.get(4), places.get(3),
                "명동 쇼핑 & 맛집", "외국인 관광객들과 함께 북적이는 명동에서 쇼핑했어요.",
                LocalDate.now().minusDays(6), true);

        createPost(users.get(4), albums.get(4), places.get(4),
                "남산타워 데이트", "연인과 함께 올라간 남산타워에서 사랑의 자물쇠를 걸었어요.",
                LocalDate.now().minusDays(11), true);

        createPost(users.get(4), albums.get(4), places.get(7),
                "부산역 KTX 여행", "KTX를 타고 부산으로 떠나는 설렘 가득한 여행!",
                LocalDate.now().minusDays(17), true);

        // 사용자들이 다양한 장소에 추가 포스트 작성
        for (int i = 0; i < 15; i++) {
            User randomUser = users.get(random.nextInt(Math.min(5, users.size()))); // 첫 5명 중 랜덤
            Album randomAlbum = albums.get(random.nextInt(Math.min(5, albums.size()))); // 첫 5개 앨범 중 랜덤
            Place randomPlace = places.get(random.nextInt(Math.min(places.size(), 20))); // 20개 장소 중 랜덤

            String[] titles = {
                    "즐거운 하루", "멋진 경험", "추억 만들기", "힐링 타임", "맛집 발견",
                    "사진 스팟", "새로운 발견", "완벽한 날씨", "친구들과 함께", "혼자만의 시간",
                    "로컬 맛집", "숨은 명소", "인생샷 성공", "여유로운 오후", "특별한 경험"
            };

            String[] contents = {
                    "정말 좋은 시간을 보냈어요!", "다시 오고 싶은 곳이에요.",
                    "추천하고 싶은 장소입니다.", "생각보다 훨씬 좋았어요!",
                    "사진이 정말 잘 나오는 곳이에요.", "분위기가 너무 좋았습니다.",
                    "친구들에게 꼭 추천할게요!", "힐링이 되는 공간이었어요.",
                    "맛있는 음식도 많고 좋았어요.", "다음에도 꼭 다시 방문하고 싶어요!",
                    "예상보다 훨씬 멋진 곳이었어요.", "여기서 보낸 시간이 너무 소중해요.",
                    "새로운 경험을 할 수 있었어요.", "정말 인상 깊은 하루였습니다.",
                    "가족들과 함께 오기 좋은 곳이에요."
            };

            createPost(randomUser, randomAlbum, randomPlace,
                    titles[random.nextInt(titles.length)],
                    contents[random.nextInt(contents.length)],
                    LocalDate.now().minusDays(random.nextInt(60) + 1),
                    random.nextBoolean());
        }

        // SecurityContext 정리
        SecurityContextHolder.clearContext();
    }

    private void createPost(User user, Album album, Place place, String title,
                            String content, LocalDate visitDate, Boolean isPublic) {
        setSecurityContext(user.getEmail());

        Post post = Post.builder()
                .content(content)
                .visitDate(visitDate)
                .isPublic(isPublic)
                .album(album)
                .place(place)
                .build();

        postRepository.save(post);
    }

    private void setSecurityContext(String email) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(email, null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}