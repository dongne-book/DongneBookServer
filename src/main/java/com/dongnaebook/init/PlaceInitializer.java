package com.dongnaebook.init;

import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.place.PlaceRepository;
import com.dongnaebook.domain.region.Region;
import com.dongnaebook.domain.region.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class PlaceInitializer implements ApplicationRunner {

    private final PlaceRepository placeRepository;
    private final RegionRepository regionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (placeRepository.count() > 0) {
            return; // 이미 데이터가 있으면 스킵
        }

        // 서울 지역들
        createPlace("스타벅스 강남점", "서울시 강남구 테헤란로 152", "강남구");
        createPlace("롯데월드타워", "서울시 송파구 올림픽로 300", "송파구");
        createPlace("경복궁", "서울시 종로구 사직로 161", "종로구");
        createPlace("홍대 클럽", "서울시 마포구 양화로 188", "마포구");
        createPlace("이태원 맛집", "서울시 용산구 이태원로 27길", "용산구");

        // 부산 지역들
        createPlace("해운대 해수욕장", "부산시 해운대구 해운대해변로 264", "해운대구");
        createPlace("감천문화마을", "부산시 사하구 감내2로 203", "사하구");
        createPlace("부산역", "부산시 동구 중앙대로 206", "동구");
        createPlace("국제시장", "부산시 중구 신창동4가", "중구");
        createPlace("광안대교", "부산시 수영구 광안해변로", "수영구");

        // 대구 지역들
        createPlace("동성로 쇼핑가", "대구시 중구 동성로2가", "중구");
        createPlace("83타워", "대구시 달서구 두류공원로 200", "달서구");
        createPlace("앞산공원", "대구시 남구 앞산순환로", "남구");

        // 광주 지역들
        createPlace("광주 양림동", "광주시 남구 양림로", "남구");
        createPlace("5.18 기념공원", "광주시 북구 운암동", "북구");

        // 대전 지역들
        createPlace("엑스포과학공원", "대전시 유성구 대덕대로 480", "유성구");
        createPlace("대전역", "대전시 동구 중앙로 215", "동구");

        // 인천 지역들
        createPlace("인천국제공항", "인천시 중구 공항로 272", "중구");
        createPlace("송도센트럴파크", "인천시 연수구 센트럴로 196", "연수구");
        createPlace("차이나타운", "인천시 중구 차이나타운로", "중구");
    }

    private void createPlace(String name, String address, String regionName) {
        List<Region> regions = regionRepository.findBestMatchingRegion(regionName);

        if (!regions.isEmpty()) {
            Place place = Place.builder()
                    .name(name)
                    .address(address)
                    .region(regions.get(0))
                    .build();

            placeRepository.save(place);
        }
    }
}