package com.w1zer.payload;

@SuppressWarnings("unused")
public record QuoteResponse(
        Long id,

        String content,

        String author,

        String source,

        String description,

        Long idProfile
) {
}
