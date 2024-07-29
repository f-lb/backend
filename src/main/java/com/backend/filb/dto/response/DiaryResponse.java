package com.backend.filb.dto.response;

import com.backend.filb.domain.entity.Report;

import java.sql.Date;

public record DiaryResponse(
        Long diaryId,
        Date date,
        String content,
        Report report
) {
}
