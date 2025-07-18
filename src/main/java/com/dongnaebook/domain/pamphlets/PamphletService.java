package com.dongnaebook.domain.pamphlets;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.gpt.GptClient;
import com.dongnaebook.domain.pamphlets.DTO.PamphletRequestDTO;
import com.dongnaebook.domain.pamphlets.DTO.PamphletResponseDTO;
import com.dongnaebook.domain.place.DTO.PlaceSimpleDTO;
import com.dongnaebook.domain.place.PlaceService;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import com.dongnaebook.domain.post.PostService;
import com.dongnaebook.domain.region.DTO.RegionResponseDTO;
import com.dongnaebook.domain.region.RegionMapper;
import com.dongnaebook.domain.region.RegionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PamphletService {
    private final PamphletRepository pamphletRepository;
    private final GptClient gptClient; // GPT 호출용 커스텀 클라이언트 (예: REST 또는 OpenAI SDK)
    private final PlaceService placeService;
    private final PostService postService;
    private final RegionService regionService;

    public Pamphlet getPamphletOrThrow(Long pamphletId) {
        return pamphletRepository.findById(pamphletId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. id=" + pamphletId));
    }

    @Transactional
    public PamphletResponseDTO createPamphlet(PamphletRequestDTO pamphletRequestDTO) {
        Pamphlet pamphlet = PamphletMapper.makingPamphlet(RegionMapper.toRegion(regionService.checkRegion(pamphletRequestDTO.getRegionCode())),pamphletRequestDTO);
        pamphletRepository.save(pamphlet);

        return PamphletMapper.toDTO(pamphlet);
    }

    // update
    @Transactional
    public PamphletResponseDTO updatePamphlet(Long pamphletId, PamphletRequestDTO pamphletRequestDTO) {
        Pamphlet pamphlet = pamphletRepository.findById(pamphletId).orElseThrow(() -> new NotFoundException("없는 팜플랫입니다"));
        pamphlet.update(pamphletRequestDTO);
        return PamphletMapper.toDTO(pamphlet);
    }

    // 지역 이름으로 get
    @Transactional
    public Map<RegionResponseDTO, List<PamphletResponseDTO>> getPamphletByRegion(String regionName) {
        List<RegionResponseDTO> regionList = regionService.findByRegionName(regionName);

        Map<RegionResponseDTO, List<PamphletResponseDTO>> regionMap = new HashMap<>();

        for (RegionResponseDTO region : regionList) {
            regionMap.put(region, pamphletRepository.findByRegion_Code(region.getCode()).stream().map(PamphletMapper::toDTO).toList());
        }
        return regionMap;
    }

    @Transactional
    public String deletePamphlet(Long pamphletId) {
        Pamphlet pamphlet = pamphletRepository.findById(pamphletId).orElseThrow(() -> new NotFoundException("없는 팜플랫입니다"));
        pamphletRepository.delete(pamphlet);
        return pamphlet.getTitle() + "삭제 되었습니다";
    }


    /**
     * 특정 지역(regionCode)의 record를 기반으로 pamphlet 생성
     */
    public String combineContent (List<PostResponseDTO> posts) {
        return posts.stream()
                .map(PostResponseDTO::getContent)
                .collect(Collectors.joining("\n"));
    }
    @Transactional
    public PamphletResponseDTO generatePamphletByGpt(String regionCode) {
        // 1. 지역 조회
        RegionResponseDTO regionDTO = regionService.checkRegion(regionCode);

        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        String version = lastMonth.toString(); // 예: "2025-07"
        // 이미 생성되었는지 확인
        boolean exists = pamphletRepository.existsByRegion_CodeAndVersion(regionCode, version);
        if (exists) {
            throw new IllegalStateException("이번 달 팜플렛은 이미 생성되었습니다.");
        }

        // 2. 해당 지역의 Record 모으기
        List<PlaceSimpleDTO> placeList = placeService.getPlaceSimpleDTOByRegion(regionCode);
        if (placeList.isEmpty()) {
            throw new IllegalStateException("해당 지역의 장소가 없습니다.");
        }

        List<List<PostResponseDTO>> postInPlace = placeList.stream().map((place) -> postService.getPostsByPlaceIdAndMonth(place.getId(), lastMonth))
                .sorted((list1, list2) -> Integer.compare(list2.size(), list1.size()))  // 내림차순 정렬
                .limit(7)  // 상위 7개만 선택
                .toList();

        String combinedContent = postInPlace.stream()
                .map(place -> {
                    String name = place.get(0).getPlace().getName();
                    String address = place.get(0).getPlace().getAddress();
                    String content = combineContent(place); // Post 리스트를 평탄화한 content
                    return String.format("장소명: %s\n주소: %s\n내용:\n%s", name, address, content);
                })
                .collect(Collectors.joining("\n\n")); // 장소별로 두 줄 띄움

        // 4. GPT에 요청하여 요약 생성
        String content = gptClient.summarizeRecords(combinedContent);


        PamphletRequestDTO pamphletRequestDTO = PamphletRequestDTO.builder()
                .title(regionDTO.getName() + "팜플랫" )
                .content(content)
                .version(version)
                .build();
        // 5. Pamphlet 생성 및 저장
        Pamphlet pamphlet = PamphletMapper.makingPamphlet(
                RegionMapper.toRegion(regionDTO), pamphletRequestDTO);

        pamphletRepository.save(pamphlet);

        return PamphletMapper.toDTO(pamphlet);
    }

}