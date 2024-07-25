package com.backend.filb.dto;

import com.backend.filb.domain.entity.Report;

import java.sql.Date;

public record DiaryRequest(
        Long diaryId,
        Date date,
        String content,
        Report report
) {
}
