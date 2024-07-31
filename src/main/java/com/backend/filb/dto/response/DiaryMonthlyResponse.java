package com.backend.filb.dto.response;

import java.time.LocalDateTime;

public record DiaryMonthlyResponse(
        LocalDateTime createdDate,
        Integer totalEmotion
) {
}
