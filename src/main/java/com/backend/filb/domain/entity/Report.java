package com.backend.filb.domain.entity;

import com.backend.filb.domain.entity.vo.EmotionSentence;
import com.backend.filb.dto.response.ReportResultResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.*;

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
    private Integer totalEmotionPercent;

    @Lob
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

    @Lob
    @Convert(converter = EmotionSentence.EmotionSentenceConverter.class)
    private List<EmotionSentence> emotionSentences = new ArrayList<>();

    public Report() {

    }

    public Report(Integer totalEmotion, Integer totalEmotionPercent, Emotions emotions, Integer negativeSentencePercent, Integer positiveSentencePercent, Integer totalSentenceCount, List<EmotionSentence> emotionSentences) {
        this.totalEmotion = totalEmotion;
        this.totalEmotionPercent = totalEmotionPercent;
        this.emotions = emotions;
        this.negativeSentencePercent = negativeSentencePercent;
        this.positiveSentencePercent = positiveSentencePercent;
        this.totalSentenceCount = totalSentenceCount;
        this.emotionSentences = emotionSentences;
    }

    public static Report of(ResponseEntity<Object> responseEntity, Diary diary) throws JsonProcessingException {
        Map<String, Object> map = parseResponse(responseEntity);

        Map<String, Integer> predictions = getPredictions(map);
        List<EmotionSentence> sentences = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : predictions.entrySet()) {
            sentences.add(new EmotionSentence((String) entry.getKey(), (Integer) entry.getValue()));
        }
        int[] emotionCounts = countEmotions(predictions);

        int totalSentences = calculateTotalSentences(emotionCounts);
        int[] emotionPercentages = calculateEmotionPercentages(emotionCounts, totalSentences);

        return createReport(emotionPercentages, totalSentences, diary, sentences);
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

    private static Report createReport(int[] emotionPercentages, int totalSentences, Diary diary, List<EmotionSentence> sentences) throws JsonProcessingException {
        int totalEmotionIndex = findMaxEmotionIndex(emotionPercentages);
        diary.updateTotalEmotion(totalEmotionIndex);
        int totalEmotionPercent = emotionPercentages[totalEmotionIndex];

        Emotions emotions = new Emotions(
                emotionPercentages[0], emotionPercentages[1], emotionPercentages[2],
                emotionPercentages[3], emotionPercentages[4], emotionPercentages[5]
        );

        int positiveSentencePercent = emotionPercentages[0];
        int negativeSentencePercent = 100 - positiveSentencePercent - emotionPercentages[5];

        return new Report(totalEmotionIndex, totalEmotionPercent, emotions, negativeSentencePercent, positiveSentencePercent, totalSentences, sentences);
    }

    private static int findMaxEmotionIndex(int[] emotionPercentages) {
        int maxIndex = 0;
        for (int i = 1; i < NUMBER_OF_EMOTION; i++) {
            if (emotionPercentages[i] > emotionPercentages[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static ReportResultResponse toDto(Diary diary, Report report) {
        return new ReportResultResponse(
                diary.getCreatedDate(),
                report.getEmotions(),
                report.getTotalEmotion(),
                report.getTotalEmotionPercent(),
                report.getFeedback(),
                report.getTotalSentenceCount(),
                report.getPositiveSentencePercent(),
                report.getNegativeSentencePercent(),
                report.getEmotionSentences()
        );
    }

    public void setFeedback(String feedBack) {
        this.feedback = feedBack;
    }
}
