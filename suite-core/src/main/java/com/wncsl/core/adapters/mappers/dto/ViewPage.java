package com.wncsl.core.adapters.mappers.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.wncsl.core.adapters.mappers.dto.View.*;

public class ViewPage<T> extends org.springframework.data.domain.PageImpl<T> {

    public ViewPage(final List<T> content, final Pageable pageable, final long total) {
        super(content, pageable, total);
    }

    public ViewPage(final List<T> content) {
        super(content);
    }

    public ViewPage(final Page<T> page, final Pageable pageable) {
        super(page.getContent(), pageable, page.getTotalElements());
    }

    @JsonView({Full.class, Resume.class })
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @JsonView({Full.class, Resume.class })
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @JsonView({Full.class, Resume.class })
    public boolean hasNext() {
        return super.hasNext();
    }

    @JsonView({Full.class, Resume.class })
    public boolean isLast() {
        return super.isLast();
    }

    @JsonView({Full.class, Resume.class })
    public boolean hasContent() {
        return super.hasContent();
    }

    @JsonView({Full.class, Resume.class })
    public List<T> getContent() {
        return super.getContent();
    }
}