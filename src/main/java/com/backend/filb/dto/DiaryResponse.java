package com.backend.filb.dto;

import java.sql.Date;

public record DiaryResponse(
        Long diaryId,
        Date date,
        String content
) {
}
