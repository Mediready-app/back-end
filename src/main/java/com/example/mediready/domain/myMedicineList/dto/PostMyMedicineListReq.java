package com.example.mediready.domain.myMedicineList.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PostMyMedicineListReq {

    private Long userId;
    private int medicineId;
    private LocalDate expirationDate;
    private Long folderId;
}
