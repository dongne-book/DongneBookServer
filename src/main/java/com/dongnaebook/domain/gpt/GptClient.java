package com.dongnaebook.domain.gpt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class GptClient {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate;

    public String summarizeRecords(String inputText) {
        Map<String, Object> message = Map.of(
                "role", "user",
                "content", "아래는 {{지역명}}에서 수집한 다양한 지역 기록입니다.  \n" +
                        "이 내용을 요약해서 관광객이 흥미를 느낄 수 있도록 3~5문장으로 설명하고, 마지막에 이 지역을 방문해야 하는 이유를 강조해줘." + inputText
        );

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(message));
        requestBody.put("temperature", 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<GptResponse> response = restTemplate.postForEntity(apiUrl, entity, GptResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody().getChoices().get(0).getMessage().getContent();
            } else {
                log.error("GPT 응답 실패: {}", response.getStatusCode());
                return "GPT 요약 실패 (status: " + response.getStatusCode() + ")";
            }
        } catch (Exception e) {
            log.error("GPT 호출 중 예외 발생", e);
            return "GPT 호출 중 오류 발생";
        }
    }
}