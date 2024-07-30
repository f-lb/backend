package com.backend.filb.infra;

import com.backend.filb.domain.entity.Message;
import com.backend.filb.dto.request.DiaryRequestToAi;
import com.backend.filb.dto.request.DiaryRequestToGpt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        String messages = content + "위 일기에서의 감정을 분석해주고, 공감해주면서 해결책을 제시해줘";

        Message message = new Message("user",messages);
        List<Message> messageList = new LinkedList<>();
        messageList.add(message);

        DiaryRequestToGpt diaryRequestToGpt = new DiaryRequestToGpt("gpt-3.5-turbo",messageList);

        String jsonBody = objectMapper.writeValueAsString(diaryRequestToGpt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.add("Authorization","Bearer 비밀키");

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(chatGptUrl, HttpMethod.POST, requestEntity, Object.class);
        System.out.println(responseEntity.getBody());
        return restTemplate.exchange(chatGptUrl, HttpMethod.POST, requestEntity, Object.class);
    }
}
