package com.example.mediready.domain.medicine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetMedicineInfoRes {

    @JsonProperty("ITEM_SEQ")
    private String itemSeq;

    @JsonProperty("ITEM_NAME")
    private String itemName;

    @JsonProperty("ENTP_NAME")
    private String entpName;

    @JsonProperty("ETC_OTC_CODE")
    private String etcOtcCode;

    @JsonProperty("CHART")
    private String chart;

    @JsonProperty("EE_DOC_DATA")
    private String eeDocData;

    @JsonProperty("UD_DOC_DATA")
    private String udDocData;

    @JsonProperty("NB_DOC_DATA")
    private String nbDocData;
}