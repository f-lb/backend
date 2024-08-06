package com.backend.filb.dto.response;

import com.backend.filb.domain.entity.Emotions;
import com.backend.filb.domain.entity.vo.EmotionSentence;

import java.time.LocalDateTime;
import java.util.List;

public record ReportResultResponse(
    Long diaryId,
    LocalDateTime createdDate,
    Emotions emotions,
    Integer totalEmotionType,
    Integer totalEmotionPercent,
    String feedback,
    Integer totalSentenceCount,
    Integer positiveSentencePercent,
    Integer negativeSentencePercent,
    List<EmotionSentence> emotionSentences,
    List<MonthlyEmotionResponse> monthlyEmotionResponse
) {
}
