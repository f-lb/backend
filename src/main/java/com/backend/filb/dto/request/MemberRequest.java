package com.backend.filb.dto.request;

import lombok.NonNull;

public record MemberRequest(
        @NonNull String email,
        @NonNull String password,
        @NonNull String name
) {
}
