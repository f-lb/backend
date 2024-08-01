package com.backend.filb.dto.response;

import java.time.LocalDateTime;

public record DiaryMonthlyResponse(
        Long diaryId,
        String title,
        String contents,
        LocalDateTime createdDate,
        Integer totalEmotion
) {
    public DiaryMonthlyResponse updatePrefix() {
        String updatedContents = this.contents;
        if (this.contents.length() > 60) {
            updatedContents = this.contents.substring(0, 60) + "...";
        }
        return new DiaryMonthlyResponse(diaryId, title, updatedContents, createdDate, totalEmotion);
    }
}
