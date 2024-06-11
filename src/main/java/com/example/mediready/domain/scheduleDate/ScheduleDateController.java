package com.example.mediready.domain.scheduleDate;

import com.example.mediready.domain.scheduleDate.dto.ScheduleReq;
import com.example.mediready.domain.scheduleDate.dto.GetScheduleDur;
import com.example.mediready.domain.scheduleDate.dto.ScheduleRes;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.BaseResponse;
import com.example.mediready.global.config.auth.AuthUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        @RequestBody ScheduleReq request) {
        scheduleDateService.createSchedule(user, request);
        return new BaseResponse<>("복용 일정이 추가되었습니다.");
    }

    @PostMapping("/dur")
    public BaseResponse<List<GetScheduleDur>> getScheduleDur(
        @RequestBody List<Integer> medicineIdList) {
        return new BaseResponse<>("복용 일정에 추가된 의약품간의 병용 가능 여부 정보입니다.",
            scheduleDateService.getScheduleDur(medicineIdList));
    }

    @PutMapping("/{id}")
    public BaseResponse<String> modifySchedule(@PathVariable Long id, @RequestBody
    ScheduleReq request) {
        scheduleDateService.modifySchedule(id, request);
        return new BaseResponse<>("복용 일정 내용이 수정되었습니다.");
    }

    @GetMapping("/{id}")
    public BaseResponse<ScheduleRes> getSchedule(@PathVariable Long id) {
        return new BaseResponse<>("복용 일정 상세 내용입니다.", scheduleDateService.getSchedule(id));
    }

    @PatchMapping("/{id}")
    public BaseResponse<String> setScheduleTaken(@PathVariable Long id) {
        scheduleDateService.setScheduleTaken(id);
        return new BaseResponse<>("복용 일정 체크 처리되었습니다.");
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteSchedule(@PathVariable Long id) {
        scheduleDateService.deleteSchedule(id);
        return new BaseResponse<>("복용 일정이 삭제되었습니다.");
    }

    @GetMapping("/upcoming")
    public BaseResponse<List<ScheduleRes>> getUpcomingSchedule(@AuthUser User user) {
        return new BaseResponse<>("앞으로의 일정 리스트입니다.",scheduleDateService.getUpcomingSchedule(user) );
    }
}
