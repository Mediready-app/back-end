package com.example.mediready.domain.myMedicineList.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class
AddMyMedicineReq {

    private Long userId;
    private int medicineId;
    private LocalDate expirationDate;
    private Long folderId;
}
