package com.backend.filb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MonthlyEmotionResponse {
    private LocalDate createdDate;
    private Integer positiveEmotionPercent;
    private Integer negativeEmotionPercent;

    public MonthlyEmotionResponse(LocalDateTime createdDate, Integer positiveEmotionPercent, Integer negativeEmotionPercent) {
        this.createdDate = createdDate.toLocalDate();
        this.positiveEmotionPercent = positiveEmotionPercent;
        this.negativeEmotionPercent = negativeEmotionPercent;
    }

}
