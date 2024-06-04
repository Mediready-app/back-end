package com.example.mediready.domain.myMedicineList.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMyMedicineRes {

    private int medicineId;
    private String name;
    private String imgUrl;
    private LocalDate expirationDate;
}
