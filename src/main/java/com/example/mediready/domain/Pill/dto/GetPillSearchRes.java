package com.example.mediready.domain.Pill.dto;

import com.example.mediready.domain.medicine.Medicine;
import lombok.Data;

@Data
public class GetPillSearchRes {

    private int id;
    private String name;
    private boolean isGeneral;
    private String imgUrl;
    private boolean stored;

    public GetPillSearchRes(Medicine medicine, boolean stored) {
        this.id = medicine.getId();
        this.name = medicine.getName();
        this.isGeneral = medicine.getIsGeneral();
        this.imgUrl = medicine.getImgUrl();
        this.stored = stored;
    }
}
