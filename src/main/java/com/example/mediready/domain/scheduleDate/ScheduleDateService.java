package com.example.mediready.domain.scheduleDate;

import com.example.mediready.domain.dur.DurRepository;
import com.example.mediready.domain.medicine.Medicine;
import com.example.mediready.domain.medicine.MedicineRepository;
import com.example.mediready.domain.medicine.dto.ScheduleMedicineRes;
import com.example.mediready.domain.scheduleDate.dto.ScheduleReq;
import com.example.mediready.domain.scheduleDate.dto.GetScheduleDur;
import com.example.mediready.domain.scheduleDate.dto.ScheduleRes;
import com.example.mediready.domain.scheduleMedicine.ScheduleMedicine;
import com.example.mediready.domain.scheduleMedicine.ScheduleMedicineId;
import com.example.mediready.domain.scheduleMedicine.ScheduleMedicineRepository;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.MedicineErrorCode;
import com.example.mediready.global.config.exception.errorCode.ScheduleDateErrorCode;
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

    private final MedicineRepository medicineRepository;
    private final ScheduleDateRepository scheduleDateRepository;
    private final ScheduleMedicineRepository scheduleMedicineRepository;
    private final DurRepository durRepository;


    @Transactional
    public void createSchedule(User user, ScheduleReq request) {
        ScheduleDate scheduleDate = new ScheduleDate(
            null,
            request.getName(),
            request.getStartDate(),
            request.getEndDate(),
            request.getRepeatCycle(),
            request.getNotificationTime(),
            request.getNotificationType(),
            user
        );

        scheduleDateRepository.save(scheduleDate);
        saveScheduleMedicines(scheduleDate, request.getMedicineList());
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

    @Transactional
    public void modifySchedule(Long id, ScheduleReq request) {
        // ScheduleDate 객체 조회
        ScheduleDate scheduleDate = scheduleDateRepository.findById(id)
            .orElseThrow(() -> new BaseException(ScheduleDateErrorCode.INVALID_SCHEDULE_DATE_ID));

        // ScheduleDate 내용 수정
        scheduleDate.setName(request.getName());
        scheduleDate.setStartDate(request.getStartDate());
        scheduleDate.setEndDate(request.getEndDate());
        scheduleDate.setRepeatCycle(request.getRepeatCycle());
        scheduleDate.setNotificationTime(request.getNotificationTime());
        scheduleDate.setNotificationType(request.getNotificationType());

        List<Integer> medicineIds = request.getMedicineList();

        scheduleMedicineRepository.deleteById_ScheduleDate(scheduleDate);
        saveScheduleMedicines(scheduleDate, medicineIds);
    }

    private void saveScheduleMedicines(ScheduleDate scheduleDate, List<Integer> medicineIds) {
        for (Integer medId : medicineIds) {
            Medicine medicine = medicineRepository.findById(medId)
                .orElseThrow(() -> new BaseException(MedicineErrorCode.INVALID_MEDICINE_ID));
            ScheduleMedicine scheduleMedicine = new ScheduleMedicine(
                new ScheduleMedicineId(medicine, scheduleDate));
            scheduleMedicineRepository.save(scheduleMedicine);
        }
    }

    @Transactional(readOnly = true)
    public ScheduleRes getSchedule(Long id) {
        ScheduleDate scheduleDate = scheduleDateRepository.findById(id)
            .orElseThrow(() -> new BaseException(ScheduleDateErrorCode.INVALID_SCHEDULE_DATE_ID));

        List<ScheduleMedicine> scheduleMedicines = scheduleMedicineRepository.findById_ScheduleDate(
            scheduleDate);
        List<ScheduleMedicineRes> scheduleMedicineRes = scheduleMedicines.stream()
            .map(sm -> new ScheduleMedicineRes(
                sm.getId().getMedicine().getId(),
                sm.getId().getMedicine().getName()
            )).toList();

        return new ScheduleRes(
            scheduleDate.getId(),
            scheduleDate.getName(),
            scheduleDate.getStartDate(),
            scheduleDate.getEndDate(),
            scheduleDate.getRepeatCycle(),
            scheduleDate.getNotificationTime(),
            scheduleDate.getNotificationType(),
            scheduleMedicineRes
        );
    }
}
