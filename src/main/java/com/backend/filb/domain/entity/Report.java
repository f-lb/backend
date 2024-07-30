package com.backend.filb.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
public class Report {
    private static final int NUMBER_OF_EMOTION = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private Integer totalEmotion;

    @Column(nullable = false)
    private String feedback;

    @Embedded
    @Column(nullable = false)
    private Emotions emotions;

    @Column(nullable = false)
    private Integer negativeSentencePercent;

    @Column(nullable = false)
    private Integer positiveSentencePercent;

    @Column(nullable = false)
    private Integer totalSentenceCount;

    public Report() {

    }

    public Report(Integer totalEmotion, String feedback, Emotions emotions, Integer negativeSentencePercent, Integer positiveSentencePercent, Integer totalSentenceCount) {
        this.totalEmotion = totalEmotion;
        this.feedback = feedback;
        this.emotions = emotions;
        this.negativeSentencePercent = negativeSentencePercent;
        this.positiveSentencePercent = positiveSentencePercent;
        this.totalSentenceCount = totalSentenceCount;
    }

    public static Report from(ResponseEntity<Object> responseEntity,String content) throws JsonProcessingException {
        Map<String, Object> map = parseResponse(responseEntity);

        Map<String, Integer> predictions = getPredictions(map);
        int[] emotionCounts = countEmotions(predictions);

        int totalSentences = calculateTotalSentences(emotionCounts);
        int[] emotionPercentages = calculateEmotionPercentages(emotionCounts, totalSentences);

        int totalEmotion = calculateTotalEmotion(emotionPercentages);
        String feedback = generateFeedback(content);

        Emotions emotions = new Emotions(emotionPercentages[0], emotionPercentages[1], emotionPercentages[2],
                emotionPercentages[3], emotionPercentages[4], emotionPercentages[5]);

        int positiveSentencePercent = emotionPercentages[0];
        int negativeSentencePercent = 100 - emotionPercentages[0] - emotionPercentages[5];

        return new Report(totalEmotion, feedback, emotions, negativeSentencePercent, positiveSentencePercent, totalSentences);
    }

    private static Map<String, Object> parseResponse(ResponseEntity<Object> responseEntity) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(responseEntity.getBody());
        return mapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {});
    }

    private static Map<String, Integer> getPredictions(Map<String, Object> map) {
        return (Map<String, Integer>) map.get("predictions");
    }

    private static int[] countEmotions(Map<String, Integer> predictions) {
        int[] emotions = new int[NUMBER_OF_EMOTION];
        for (Integer value : predictions.values()) {
            if (value >= 0 && value < NUMBER_OF_EMOTION) {
                emotions[value]++;
            }
        }
        return emotions;
    }

    private static int calculateTotalSentences(int[] emotionCounts) {
        return Arrays.stream(emotionCounts).sum();
    }

    private static int[] calculateEmotionPercentages(int[] emotionCounts, int totalSentences) {
        int[] emotionPercentages = new int[NUMBER_OF_EMOTION];
        for (int i = 0; i < NUMBER_OF_EMOTION; i++) {
            emotionPercentages[i] = (int) (((double) emotionCounts[i] / totalSentences) * 100);
        }
        return emotionPercentages;
    }

    private static int calculateTotalEmotion(int[] emotionPercentages) {
        RestTemplate restTemplate = new RestTemplate();
        return 0;
    }

    private static String generateFeedback(String content) {
        // 피드백 생성 로직 필요 (임의로 빈 문자열로 설정)
        return "";
    }

}
