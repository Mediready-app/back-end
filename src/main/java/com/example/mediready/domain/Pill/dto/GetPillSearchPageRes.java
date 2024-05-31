package com.example.mediready.domain.Pill.dto;

import java.util.List;
import lombok.Data;

@Data
public class GetPillSearchPageRes<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
