package com.backend.filb.dto.response;

import com.backend.filb.domain.entity.Emotions;

import java.time.LocalDateTime;

public record ReportResultResponse(
    LocalDateTime createdDate,
    Emotions emotions,
    Integer totalEmotion,
    String feedback,
    Integer totalSentenceCount,
    Integer positiveSentencePercent,
    Integer negativeSentencePercent
) {
}
