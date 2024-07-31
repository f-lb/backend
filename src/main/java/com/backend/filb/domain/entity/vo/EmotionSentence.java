package com.backend.filb.domain.entity.vo;

import com.backend.filb.common.JsonArrayConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class EmotionSentence {

    private String sentence;
    private Integer emotionType;

    public static class EmotionSentenceConverter extends JsonArrayConverter<EmotionSentence> {
        public EmotionSentenceConverter() {
            super(new TypeReference<>() {}, new ObjectMapper()); // 1
        }
    }
}
