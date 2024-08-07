package com.w1zer.controller;

import com.w1zer.entity.Tag;
import com.w1zer.payload.QuoteResponse;
import com.w1zer.payload.TagRequest;
import com.w1zer.service.TagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.w1zer.constants.ValidationConstants.ID_POSITIVE_MESSAGE;

@RestController
@Validated
@CrossOrigin
@RequestMapping("/api/tags")
@PreAuthorize("hasRole('ADMIN')")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PutMapping("/{id}")
    public Tag replace(@Valid @RequestBody TagRequest tagRequest,
                       @PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id) {
        return tagService.replace(tagRequest, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id) {
        tagService.delete(id);
    }

    @GetMapping
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public Tag findById(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id) {
        return tagService.findById(id);
    }

    @PostMapping
    public void create(@Valid @RequestBody TagRequest tagRequest) {
        tagService.create(tagRequest);
    }

    @GetMapping("/{id}/quotes")
    public Set<QuoteResponse> getQuotes(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id) {
        return tagService.getQuotes(id);
    }

    @PutMapping("/{tagId}/quotes/{quoteId}")
    public void addQuote(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long tagId,
                         @PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long quoteId) {
        tagService.addQuote(tagId, quoteId);
    }

    @DeleteMapping("/{tagId}/quotes/{quoteId}")
    public void removeQuote(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long tagId,
                            @PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long quoteId) {
        tagService.removeQuote(tagId, quoteId);
    }
}
