package com.example.mediready.domain.medicine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMedicineDurInfoRes {

    private int id;
    private String name;
    private String imgUrl;
    private boolean isCompatible;
}
