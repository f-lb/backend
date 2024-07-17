package com.backend.filb.dto;

public record MemberRequest(
        String email,
        String password,
        String name
) {
}
