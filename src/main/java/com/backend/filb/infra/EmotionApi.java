package com.backend.filb.infra;

import com.backend.filb.dto.request.DiaryRequestToAi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class EmotionApi {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiUrl = "http://localhost:8000/predict";

    public EmotionApi(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<Object> getEmotionResponse(String content) throws JsonProcessingException {
        DiaryRequestToAi diaryRequestToAi = new DiaryRequestToAi(content);
        String jsonBody = objectMapper.writeValueAsString(diaryRequestToAi);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        return restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Object.class);
    }
}
