package com.dongnaebook.init;

import com.dongnaebook.domain.diary.Diary;
import com.dongnaebook.domain.diary.DiaryRepository;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(5)
public class DiaryInitializer implements ApplicationRunner {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (diaryRepository.count() > 0) {
            return; // 이미 데이터가 있으면 스킵
        }

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return; // 사용자가 없으면 스킵
        }

        // 지영이의 일기들
        createDiary(users.get(0), "강남에서의 달콤한 오후",
                "친구들과 강남 스타벅스에서 만나서 수다를 떨었다. 새로 나온 음료도 마셔보고 근처 쇼핑몰에서 예쁜 옷도 구경했다. 여자들만의 시간이라 더욱 즐거웠다.",
                "강남, 스타벅스, 친구, 쇼핑, 여자", false);

        createDiary(users.get(0), "경복궁 한복 데이트",
                "연인과 함께 경복궁에서 한복을 입고 사진을 찍었다. 전통 의상을 입으니 기분이 색달랐고, 궁궐의 아름다운 풍경이 배경이 되어 인생샷을 건졌다.",
                "경복궁, 한복, 연인, 데이트, 인생샷", false);

        createDiary(users.get(0), "송도에서의 힐링 산책",
                "송도 센트럴파크에서 혼자만의 시간을 가졌다. 인공 운하를 따라 걸으며 마음의 평화를 찾았고, 도시 속에서도 자연을 느낄 수 있어 좋았다.",
                "송도, 센트럴파크, 혼자, 힐링, 평화", false);

        createDiary(users.get(0), "인천공항 면세점 쇼핑",
                "해외여행 가기 전 인천공항 면세점에서 쇼핑했다. 화장품과 향수를 둘러보며 여행에 대한 설렘을 키웠다.",
                "인천공항, 면세점, 쇼핑, 화장품, 설렘", false);

        // 효식이의 일기들
        createDiary(users.get(1), "롯데월드타워 야경 감상",
                "서울스카이에서 내려다본 서울의 야경이 정말 장관이었다. 118층 높이에서 보는 도시의 불빛들이 마치 별처럼 반짝였다.",
                "롯데월드타워, 서울스카이, 야경, 불빛, 별", false);

        createDiary(users.get(1), "해운대 일출 사진 촬영",
                "새벽 5시에 일어나서 해운대에서 일출을 촬영했다. 바다 위로 떠오르는 태양을 카메라에 담으며 자연의 위대함을 느꼈다.",
                "해운대, 일출, 사진촬영, 태양, 자연", false);

        createDiary(users.get(1), "광안대교 드라이브 데이트",
                "연인과 함께 광안대교를 드라이브했다. 야경이 아름다운 다리 위에서 부산의 밤바다를 만끽하며 로맨틱한 시간을 보냈다.",
                "광안대교, 드라이브, 연인, 야경, 로맨틱", false);

        createDiary(users.get(1), "부산역에서 시작된 기차여행",
                "부산역에서 KTX를 타고 전국을 여행하는 첫날이다. 기차 여행만의 특별한 설렘과 창밖으로 스쳐가는 풍경이 인상적이었다.",
                "부산역, KTX, 기차여행, 설렘, 풍경", false);

        // 서연이의 일기들
        createDiary(users.get(2), "감천문화마을 예술 탐방",
                "부산의 마추픽추라 불리는 감천문화마을을 탐방했다. 알록달록한 벽화와 조형물들이 가득해서 마치 동화 속 마을에 온 기분이었다.",
                "감천문화마을, 마추픽추, 벽화, 조형물, 동화", false);

        createDiary(users.get(2), "광주 양림동 역사 탐방",
                "양림동 근대문화거리에서 옛 선교사들의 흔적을 찾아보았다. 펭귄마을과 오웬기념각 등 역사적인 건물들이 인상 깊었다.",
                "양림동, 근대문화, 선교사, 펭귄마을, 역사", false);

        createDiary(users.get(2), "5.18 기념공원에서의 묵념",
                "5.18 민주화운동 기념공원에서 역사의 아픔을 되새겼다. 민주주의를 위해 희생하신 분들을 생각하며 숙연한 마음이 들었다.",
                "5.18, 기념공원, 민주화운동, 희생, 숙연", false);

        createDiary(users.get(2), "인천 차이나타운 문화체험",
                "인천 차이나타운에서 중국 문화를 체험했다. 원조 짜장면도 먹어보고 중국 전통 건물들도 구경하며 이국적인 분위기를 만끽했다.",
                "차이나타운, 중국문화, 짜장면, 전통건물, 이국적", false);

        // 상진이의 일기들
        createDiary(users.get(3), "동성로 쇼핑몰 투어",
                "대구 동성로에서 하루 종일 쇼핑을 즐겼다. 다양한 브랜드 매장들이 모여있어서 원하는 옷들을 찾기 쉬웠고, 맛집도 많아서 좋았다.",
                "동성로, 쇼핑몰, 브랜드, 옷, 맛집", false);

        createDiary(users.get(3), "83타워에서 본 대구 전망",
                "대구의 랜드마크 83타워 전망대에 올라갔다. 높은 곳에서 내려다본 대구 시내의 모습이 한눈에 들어와서 시원했다.",
                "83타워, 전망대, 대구시내, 랜드마크, 시원", false);

        createDiary(users.get(3), "대전 엑스포과학공원 체험",
                "대전 엑스포과학공원에서 다양한 과학 체험을 했다. 첨단 기술들을 직접 만져보고 체험할 수 있어서 신기했다.",
                "대전, 엑스포, 과학공원, 체험, 첨단기술", false);

        // 지현이의 일기들
        createDiary(users.get(4), "명동 쇼핑 & 맛집 투어",
                "명동에서 외국인 친구들과 함께 쇼핑하고 맛집을 돌아다녔다. 국제적인 분위기의 거리에서 다양한 문화를 경험할 수 있었다.",
                "명동, 외국인친구, 쇼핑, 맛집, 국제적", false);

        createDiary(users.get(4), "남산타워 로맨틱 데이트",
                "연인과 함께 남산타워에 올라가서 사랑의 자물쇠를 걸었다. 서울 시내가 한눈에 보이는 곳에서 영원한 사랑을 약속했다.",
                "남산타워, 연인, 사랑의자물쇠, 서울시내, 영원한사랑", false);

        createDiary(users.get(4), "부산역 KTX 탑승기",
                "전국 여행의 시작점인 부산역에서 KTX에 탑승했다. 빠른 속도로 달리는 기차 안에서 창밖 풍경을 보며 여행에 대한 기대감이 커졌다.",
                "부산역, KTX, 전국여행, 속도, 기대감", false);

        // AI 자동 생성 일기들
        createDiary(users.get(0), "AI가 생성한 여행 요약",
                "오늘의 위치 데이터와 사진을 분석한 결과, 총 3개의 카페와 2개의 쇼핑몰을 방문하셨습니다. 주로 강남 지역에서 활동하시며 친구들과 즐거운 시간을 보내신 것 같네요!",
                "AI분석, 위치데이터, 카페, 쇼핑몰, 강남", true);

        createDiary(users.get(1), "AI 분석: 부산 여행 패턴",
                "사진의 메타데이터를 분석한 결과, 오늘은 바다 관련 사진이 많이 촬영되었습니다. 일출 시간대와 야경 시간대에 집중적으로 활동하신 것으로 보입니다.",
                "AI분석, 메타데이터, 바다사진, 일출, 야경", true);

        createDiary(users.get(2), "AI 요약: 문화 탐방의 하루",
                "GPS 추적 결과 박물관과 문화유적지를 중심으로 이동하셨습니다. 총 걸음 수 15,000보로 활발한 문화 탐방 활동을 하신 하루였네요.",
                "AI요약, GPS추적, 박물관, 문화유적지, 걸음수", true);

        SecurityContextHolder.clearContext();
    }

    private void createDiary(User user, String title, String content, String keywords, boolean isAutoGenerated) {
        setSecurityContext(user.getEmail());

        Diary diary = Diary.builder()
                .title(title)
                .content(content)
                .keywords(keywords)
                .isAutoGenerated(isAutoGenerated)
                .build();

        diaryRepository.save(diary);
    }

    private void setSecurityContext(String email) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(email, null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}