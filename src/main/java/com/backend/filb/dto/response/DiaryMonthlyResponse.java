package com.backend.filb.dto.response;

import java.time.LocalDateTime;

public record DiaryMonthlyResponse(
        Long diaryId,
        LocalDateTime createdDate,
        Integer totalEmotion
) {
}
