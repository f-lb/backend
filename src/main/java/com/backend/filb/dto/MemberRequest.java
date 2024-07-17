package com.backend.filb.dto;

public record MemberRequest(
        Long memberId,
        String email,
        String password,
        String name
) {
}
