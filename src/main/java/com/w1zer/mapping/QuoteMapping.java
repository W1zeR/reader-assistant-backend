package com.w1zer.mapping;

import com.w1zer.entity.Book;
import com.w1zer.entity.Profile;
import com.w1zer.entity.Quote;
import com.w1zer.payload.BookAuthorResponse;
import com.w1zer.payload.QuoteBookResponse;
import com.w1zer.payload.QuoteProfileResponse;
import com.w1zer.payload.QuoteResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class QuoteMapping {
    private QuoteMapping() {
    }

    public static Page<QuoteResponse> mapToQuoteResponsesPage(Page<Quote> quotesPage) {
        return quotesPage.map(QuoteMapping::mapToQuoteResponse);
    }

    public static List<QuoteResponse> mapToQuoteResponses(List<Quote> quotes) {
        return quotes
                .stream()
                .map(QuoteMapping::mapToQuoteResponse)
                .collect(Collectors.toList());
    }

    public static Set<QuoteResponse> mapToQuoteResponses(Set<Quote> quotes) {
        return quotes
                .stream()
                .map(QuoteMapping::mapToQuoteResponse)
                .collect(Collectors.toSet());
    }

    public static QuoteResponse mapToQuoteResponse(Quote quote) {
        Book book = quote.getBook();
        QuoteBookResponse quoteBookResponse = new QuoteBookResponse(book.getId(), book.getTitle(),
                getBookAuthors(book));
        LocalDateTime localDateTime = quote.getChangeDate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String changeDate = localDateTime.format(dateTimeFormatter);
        return new QuoteResponse(quote.getId(), quote.getContent(), quoteBookResponse, quote.getStatus(),
                quote.getTags(), mapToQuoteProfileResponse(quote.getProfile()), quote.getLikesCount(),
                changeDate);
    }

    private static QuoteProfileResponse mapToQuoteProfileResponse(Profile profile) {
        return new QuoteProfileResponse(profile.getId(), profile.getLogin());
    }

    private static Set<BookAuthorResponse> getBookAuthors(Book book) {
        return book.getAuthors()
                .stream()
                .map(a -> new BookAuthorResponse(a.getId(), a.getSurname(), a.getName(), a.getPatronymic()))
                .collect(Collectors.toSet());
    }
}
