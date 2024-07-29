package com.backend.filb.dto.request;

import lombok.NonNull;

public record MemberLoginRequest(
        @NonNull String email,
        @NonNull String password
) {
}
