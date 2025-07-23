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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PamphletService {
    private final PamphletRepository pamphletRepository;
    private final GptClient gptClient; // GPT 호출용 커스텀 클라이언트 (예: REST 또는 OpenAI SDK)
    private final PlaceService placeService;
    private final PostService pamphletService;
    private final RegionService regionService;

    @Transactional(readOnly = true)
    public PamphletResponseDTO getById(Long id) {
        Pamphlet pamphlet = pamphletRepository.findById(id).orElseThrow(() -> new NotFoundException("Pamphlet not found"));
        return PamphletMapper.toResponseDto(pamphlet);
    }

    @Transactional(readOnly = true)
    public List<PamphletResponseDTO> getAll() {
        return pamphletRepository.findAll().stream()
                .map(PamphletMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PamphletResponseDTO> getPamphletsByRegionCode(String regionCode) {
        List<Pamphlet> pamphlets = pamphletRepository.findByRegion_Code(regionCode);
        return pamphlets.stream()
                .map(PamphletMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PamphletResponseDTO> getPamphletsByRegionName(String name) {
        List<Pamphlet> pamphlets = pamphletRepository.findByRegion_NameContaining(name);
        return pamphlets.stream()
                .map(PamphletMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PamphletResponseDTO createPamphlet(PamphletRequestDTO pamphletRequestDTO) {
        Pamphlet pamphlet = PamphletMapper.toEntity(RegionMapper.toRegion(regionService.checkRegion(pamphletRequestDTO.getRegionCode())),pamphletRequestDTO);
        pamphletRepository.save(pamphlet);

        return PamphletMapper.toResponseDto(pamphlet);
    }

    // update
    @Transactional
    public PamphletResponseDTO update(Long pamphletId, PamphletRequestDTO pamphletRequestDTO) {
        Pamphlet pamphlet = pamphletRepository.findById(pamphletId).orElseThrow(() -> new NotFoundException("없는 팜플랫입니다"));
        pamphlet.update(pamphletRequestDTO);
        return PamphletMapper.toResponseDto(pamphlet);
    }

    @Transactional
    public void deleteById(Long pamphletId) {
        Pamphlet pamphlet = pamphletRepository.findById(pamphletId).orElseThrow(() -> new NotFoundException("없는 팜플랫입니다"));
        pamphletRepository.delete(pamphlet);
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
    public PamphletResponseDTO create(String regionCode) {
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

        List<List<PostResponseDTO>> postInPlace = placeList.stream().map((place) -> pamphletService.getPostsByPlaceIdAndMonth(place.getId(), lastMonth))
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
        Pamphlet pamphlet = PamphletMapper.toEntity(
                RegionMapper.toRegion(regionDTO), pamphletRequestDTO);

        pamphletRepository.save(pamphlet);

        return PamphletMapper.toResponseDto(pamphlet);
    }

}