package com.w1zer.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

import static com.w1zer.constants.EntityConstants.CONTENT_LENGTH;

@SuppressWarnings("unused")
public record QuoteRequest(
        @NotBlank
        @Size(max = CONTENT_LENGTH)
        String content,

        @Valid
        QuoteBookRequest book,

        Set<@Valid TagRequest> tags
) {
}
