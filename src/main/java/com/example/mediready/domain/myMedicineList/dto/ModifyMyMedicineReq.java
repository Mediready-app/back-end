package com.example.mediready.domain.myMedicineList.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ModifyMyMedicineReq {

    private LocalDate expirationTime;
    private Long folderId;

}
