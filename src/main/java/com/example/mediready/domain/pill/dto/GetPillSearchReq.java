package com.example.mediready.domain.pill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPillSearchReq {

    private String frontMark;
    private String backMark;
    private String color;
    private String form;
    private String shape;

}
