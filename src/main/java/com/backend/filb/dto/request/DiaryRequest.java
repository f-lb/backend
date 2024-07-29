package com.backend.filb.dto.request;

import java.time.LocalDateTime;

public record DiaryRequest(
        LocalDateTime date,
        String content
) {
}
