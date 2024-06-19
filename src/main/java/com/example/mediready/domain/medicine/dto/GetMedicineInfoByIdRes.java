package com.example.mediready.domain.medicine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMedicineInfoByIdRes {

    private int id;
    private String name;
    private String imgUrl;
}
