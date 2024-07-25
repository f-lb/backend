package com.backend.filb;

import com.backend.filb.dto.DiaryRequestToAi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateTest {
    @Test
    void test1() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        DiaryRequestToAi diaryRequestToAi = new DiaryRequestToAi("오늘은 기분이 좋았다. 아 그런데 숙제 해야하네. 그건 좀 그렇댜. 아 너무 졸린데 할게 너무 많다.");
        var url = "http://localhost:8000/predict";

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(diaryRequestToAi);

        // Create headers
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HttpEntity
        var requestEntity = new HttpEntity<>(jsonBody,headers);

        // POST request
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                Object.class);

        System.out.println("결과값");
        System.out.println(responseEntity.getBody());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(responseEntity.getBody());

        Map<String, Object> map = mapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {});

        Map<String, Integer> predictions = (Map<String, Integer>) map.get("predictions");


        System.out.println(predictions.size());

        // 배열 생성
        int[] emotions = new int[5];

        // 각 value 값의 등장 횟수를 세어서 배열에 저장
        for (Integer value : predictions.values()) {
            if (value >= 0 && value < emotions.length) {
                emotions[value]++;
            }
        }

    }
}
