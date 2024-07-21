package com.backend.filb.dto;

import com.backend.filb.domain.entity.Diary;

import java.util.List;

public record MemberRequest(
        String email,
        String password,
        String name
) {
}
