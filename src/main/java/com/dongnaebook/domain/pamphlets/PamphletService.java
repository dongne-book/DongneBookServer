package com.dongnaebook.domain.pamphlets;

import com.dongnaebook.domain.gpt.GptClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PamphletService {
    private final PamphletRepository pamphletRepository;
    private final GptClient gptClient; // GPT 호출용 커스텀 클라이언트 (예: REST 또는 OpenAI SDK)
//    private final RecordService recordService;

    /**
     * 특정 지역(regionCode)의 record를 기반으로 pamphlet 생성
     */
//    public PamphletDTO createPamphletByRegion(String regionCode) {
//        // 1. 지역 조회
//        Region region = regionRepository.findById(regionCode)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역 코드입니다: " + regionCode));
//
//        // 2. 해당 지역의 Record 모으기
//        List<Record> records = recordService.findAllByRegion(region);
//
//        if (records.isEmpty()) {
//            throw new IllegalStateException("해당 지역에 기록된 Record가 없습니다.");
//        }
//
//        // 3. Record 내용을 하나로 합치기
//        String combinedContent = records.stream()
//                .map(Record::getContent)
//                .collect(Collectors.joining("\n"));
//
//        // 4. GPT에 요청하여 요약 생성
//        String summary = gptClient.summarizeRecords(combinedContent);
//
//        // 5. Pamphlet 생성 및 저장
//        Pamphlet pamphlet = Pamphlet.builder()
//                .region(region)
//                .summary(summary)
//                .build();
//
//        return pamphletRepository.save(pamphlet);

}