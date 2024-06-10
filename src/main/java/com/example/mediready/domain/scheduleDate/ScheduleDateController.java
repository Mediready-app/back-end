package com.example.mediready.domain.scheduleDate;

import com.example.mediready.domain.scheduleDate.dto.CreateScheduleReq;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.BaseResponse;
import com.example.mediready.global.config.auth.AuthUser;
import lombok.RequiredArgsConstructor;
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
}
