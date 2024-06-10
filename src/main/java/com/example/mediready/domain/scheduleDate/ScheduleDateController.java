package com.example.mediready.domain.scheduleDate;

import com.example.mediready.domain.scheduleDate.dto.CreateScheduleReq;
import com.example.mediready.domain.scheduleDate.dto.GetScheduleDur;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.BaseResponse;
import com.example.mediready.global.config.auth.AuthUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleDateController {

    private final ScheduleDateService scheduleDateService;

    @PostMapping
    public BaseResponse<String> createSchedule(@AuthUser User user,
        @RequestBody CreateScheduleReq createScheduleReq) {
        scheduleDateService.createSchedule(user, createScheduleReq);
        return new BaseResponse<>("복용 일정이 추가되었습니다.");
    }

    @GetMapping("/dur")
    public BaseResponse<List<GetScheduleDur>> getScheduleDur(
        @RequestBody List<Integer> medicineIdList) {
        return new BaseResponse<>("복용 일정에 추가된 의약품간의 병용 가능 여부 정보입니다.",
            scheduleDateService.getScheduleDur(medicineIdList));
    }
}
