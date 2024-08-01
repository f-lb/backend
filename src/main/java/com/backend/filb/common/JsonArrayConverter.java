package com.backend.filb.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.StringUtils;

import java.util.List;

@Converter
public class JsonArrayConverter<T> implements AttributeConverter<List<T>, String> { // 1

    private final TypeReference<List<T>> typeReference; // 2
    private final ObjectMapper objectMapper;

    public JsonArrayConverter(TypeReference<List<T>> typeReference, ObjectMapper objectMapper) {
        this.typeReference = typeReference;
        this.objectMapper = objectMapper;
    }

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        if (StringUtils.hasText(dbData)) {
            try {
                return objectMapper.readValue(dbData, typeReference);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}