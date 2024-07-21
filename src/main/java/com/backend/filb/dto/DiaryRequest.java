package com.backend.filb.dto;

import java.sql.Date;

public record DiaryRequest(
        Long diaryId,
        Date date,
        String content
) {
}
