package com.example.mediready.domain.folder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetFolderRes {

    private Long id;
    private String name;
    private int priority;
}
