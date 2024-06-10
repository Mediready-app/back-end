package com.example.mediready.domain.scheduleMedicine;

import com.example.mediready.domain.scheduleDate.ScheduleDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleMedicineRepository extends
    JpaRepository<ScheduleMedicine, ScheduleMedicineId> {


    void deleteById_ScheduleDate(ScheduleDate scheduleDate);

    List<ScheduleMedicine> findById_ScheduleDate(ScheduleDate scheduleDate);
}
