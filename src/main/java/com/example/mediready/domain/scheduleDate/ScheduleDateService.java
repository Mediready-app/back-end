package com.example.mediready.domain.scheduleDate;

import com.example.mediready.domain.dur.DurRepository;
import com.example.mediready.domain.medicine.Medicine;
import com.example.mediready.domain.medicine.MedicineRepository;
import com.example.mediready.domain.scheduleDate.dto.CreateScheduleReq;
import com.example.mediready.domain.scheduleDate.dto.GetScheduleDur;
import com.example.mediready.domain.scheduleMedicine.ScheduleMedicine;
import com.example.mediready.domain.scheduleMedicine.ScheduleMedicineId;
import com.example.mediready.domain.scheduleMedicine.ScheduleMedicineRepository;
import com.example.mediready.domain.user.User;
import com.example.mediready.domain.user.UserRepository;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.MedicineErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    private final DurRepository durRepository;


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

    public List<GetScheduleDur> getScheduleDur(List<Integer> medicineIdList) {
        List<Medicine> medicines = medicineRepository.findAllById(medicineIdList);
        if (medicines.size() != medicineIdList.size()) {
            throw new BaseException(MedicineErrorCode.INVALID_MEDICINE_ID);
        }

        Map<Integer, Medicine> medicineMap = medicines.stream()
            .collect(Collectors.toMap(Medicine::getId, medicine -> medicine));

        List<GetScheduleDur> getScheduleDurs = new ArrayList<>();

        for (int i = 0; i < medicineIdList.size(); i++) {
            for (int j = i + 1; j < medicineIdList.size(); j++) {
                Medicine medicine1 = medicineMap.get(medicineIdList.get(i));
                Medicine medicine2 = medicineMap.get(medicineIdList.get(j));

                if (durRepository.existsByDurIdPk_Id1AndDurIdPk_Id2(medicine1, medicine2)
                    || durRepository.existsByDurIdPk_Id1AndDurIdPk_Id2(medicine2, medicine1)) {
                    getScheduleDurs.add(
                        new GetScheduleDur(medicine1.getName(), medicine2.getName()));
                }
            }
        }
        return getScheduleDurs;
    }
}
