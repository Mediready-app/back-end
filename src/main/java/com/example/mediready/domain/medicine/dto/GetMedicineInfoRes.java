package com.example.mediready.domain.medicine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetMedicineInfoRes {

    private int itemSeq;
    private String itemName;
    private String entpName;
    private String etcOtcCode;
    private String imgUrl;
    private String eeDocData;
    private String udDocData;
    private String nbDocData;
    private boolean isSaved;
}