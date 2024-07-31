package com.backend.filb.dto.request;

import com.backend.filb.domain.entity.Message;

import java.util.List;

public record DiaryRequestToGpt(
        String model,
        List<Message> messages
) {
}
