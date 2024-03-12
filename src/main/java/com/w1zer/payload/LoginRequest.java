package com.w1zer.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static com.w1zer.constants.EntityConstants.LOGIN_LENGTH;
import static com.w1zer.constants.ValidationConstants.*;

public record LoginRequest(
        @NotBlank(message = LOGIN_NOT_BLANK_MESSAGE)
        @Size(min = LOGIN_MIN_SIZE, max = LOGIN_LENGTH, message = LOGIN_SIZE_MESSAGE)
        String login,

        @NotBlank(message = PASSWORD_NOT_BLANK_MESSAGE)
        @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE, message = PASSWORD_SIZE_MESSAGE)
        String password,

        @Valid
        @NotNull(message = DEVICE_INFO_NOT_NULL_MESSAGE)
        DeviceInfo deviceInfo
) {
        private static final String DEVICE_INFO_NOT_NULL_MESSAGE = "Device info can't be null";
}
