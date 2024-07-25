package com.backend.filb.dto;

import com.backend.filb.domain.entity.Report;

import java.sql.Date;

public record DiaryResponse(
        Long diaryId,
        Date date,
        String content,
        Report report
) {
}
