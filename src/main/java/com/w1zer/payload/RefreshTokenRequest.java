package com.w1zer.payload;

import jakarta.validation.constraints.NotBlank;

@SuppressWarnings("unused")
public record RefreshTokenRequest(
        @NotBlank(message = REFRESH_TOKEN_NOT_BLANK_MESSAGE)
        String refreshToken
) {
    private static final String REFRESH_TOKEN_NOT_BLANK_MESSAGE = "Refresh token can't be blank";
}
