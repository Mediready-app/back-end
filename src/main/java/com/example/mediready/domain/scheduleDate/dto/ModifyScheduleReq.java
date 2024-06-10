package com.example.mediready.domain.scheduleDate.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;

@Data
public class ModifyScheduleReq {

    private String name;
    private List<Integer> medicineList;
    private LocalDate startDate;
    private LocalDate endDate;
    private int repeatCycle;
    private LocalTime notificationTime;
    private String notificationType;

}