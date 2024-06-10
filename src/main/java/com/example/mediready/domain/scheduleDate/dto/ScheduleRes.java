package com.example.mediready.domain.scheduleDate.dto;

import com.example.mediready.domain.medicine.dto.ScheduleMedicineRes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleRes {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int repeatCycle;
    private LocalTime notificationTime;
    private String notificationType;
    private List<ScheduleMedicineRes> medicines;
}
