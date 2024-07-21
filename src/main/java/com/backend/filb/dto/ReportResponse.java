package com.backend.filb.dto;

import com.backend.filb.domain.entity.Emotions;
import jakarta.persistence.Embedded;

public record ReportResponse(
        Long reportId,
        Integer totalEmotion,
        String Feedback,
        @Embedded
        Emotions emotions,
        Integer negativeSentencePercent,
        Integer positiveSentencePercent,
        Integer totalSentenceCount
) {
}
