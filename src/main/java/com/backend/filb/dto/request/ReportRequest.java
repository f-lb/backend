package com.backend.filb.dto.request;

import com.backend.filb.domain.entity.Emotions;
import jakarta.persistence.Embedded;

public record ReportRequest(
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
