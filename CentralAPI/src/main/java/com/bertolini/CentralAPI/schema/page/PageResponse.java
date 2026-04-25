package com.bertolini.CentralAPI.schema.page;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        Long totalElements,
        int totalPages,
        int pageNumber
) {
    public PageResponse(Page<T> page) {
        this(page.getContent(), page.getTotalElements(), page.getTotalPages(), page.getNumber());
    }
}
