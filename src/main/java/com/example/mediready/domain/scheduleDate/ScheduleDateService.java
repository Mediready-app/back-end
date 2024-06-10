package com.example.mediready.domain.scheduleDate;

import com.example.mediready.domain.medicine.Medicine;
import com.example.mediready.domain.medicine.MedicineRepository;
import com.example.mediready.domain.scheduleDate.dto.CreateScheduleReq;
import com.example.mediready.domain.scheduleMedicine.ScheduleMedicine;
import com.example.mediready.domain.scheduleMedicine.ScheduleMedicineId;
import com.example.mediready.domain.scheduleMedicine.ScheduleMedicineRepository;
import com.example.mediready.domain.user.User;
import com.example.mediready.domain.user.UserRepository;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.MedicineErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleDateService {

    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;
    private final ScheduleDateRepository scheduleDateRepository;
    private final ScheduleMedicineRepository scheduleMedicineRepository;


    @Transactional
    public void createSchedule(User user, CreateScheduleReq createScheduleReq) {
        ScheduleDate scheduleDate = new ScheduleDate(
            null,
            createScheduleReq.getName(),
            createScheduleReq.getStartDate(),
            createScheduleReq.getEndDate(),
            createScheduleReq.getRepeatCycle(),
            createScheduleReq.getNotificationTime(),
            createScheduleReq.getNotificationType(),
            user
        );

        scheduleDateRepository.save(scheduleDate);

        List<Integer> medicineIds = createScheduleReq.getMedicineList();
        for (Integer medicineId : medicineIds) {
            Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new BaseException(MedicineErrorCode.INVALID_MEDICINE_ID));
            ScheduleMedicineId scheduleMedicineId = new ScheduleMedicineId(medicine, scheduleDate);
            ScheduleMedicine scheduleMedicine = new ScheduleMedicine(scheduleMedicineId);
            scheduleMedicineRepository.save(scheduleMedicine);
        }
    }
}
