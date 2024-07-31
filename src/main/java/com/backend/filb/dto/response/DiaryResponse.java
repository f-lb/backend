package com.backend.filb.dto.response;

import com.backend.filb.domain.entity.Report;

import java.sql.Date;
import java.time.LocalDateTime;

public record DiaryResponse(
        Long diaryId,
        LocalDateTime createdDate,
        String content
) {
}
