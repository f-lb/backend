package com.backend.filb.infra;

import com.backend.filb.domain.entity.Message;
import com.backend.filb.dto.request.DiaryRequestToAi;
import com.backend.filb.dto.request.DiaryRequestToGpt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class EmotionApi {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String analyzeEmotionApiUrl = "http://localhost:8000/predict";
    private final String chatGptUrl = "https://api.openai.com/v1/chat/completions";

    @Value("${chatgpt.api-key}")
    private String gptKey;

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
        return restTemplate.exchange(analyzeEmotionApiUrl, HttpMethod.POST, requestEntity, Object.class);
    }

    public ResponseEntity<Object> getReport(String content) throws JsonProcessingException {
        String messages = content + " 위 일기에 대해서 ~때문에 ~ 감정을 느끼신 것 같다 형식으로 감정을 공감해주고 해결책을 제시해주는 방향으로 이야기해줘.";

        Message message = new Message("user",messages);
        List<Message> messageList = new LinkedList<>();
        messageList.add(message);

        DiaryRequestToGpt diaryRequestToGpt = new DiaryRequestToGpt("gpt-3.5-turbo",messageList);

        String jsonBody = objectMapper.writeValueAsString(diaryRequestToGpt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.add("Authorization","Bearer " + gptKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        return restTemplate.exchange(chatGptUrl, HttpMethod.POST, requestEntity, Object.class);
    }
}
