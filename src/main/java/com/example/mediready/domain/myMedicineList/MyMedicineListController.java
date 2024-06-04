package com.example.mediready.domain.myMedicineList;

import com.example.mediready.domain.myMedicineList.dto.SaveMyMedicineReq;
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
@RequestMapping("/my-medicine-list")
public class MyMedicineListController {

    private final MyMedicineListService myMedicineListService;

    @PostMapping("/save")
    public BaseResponse<String> saveMyMedicine(@AuthUser User user,
        @RequestBody SaveMyMedicineReq request) {
        myMedicineListService.saveMyMedicine(user, request);
        return new BaseResponse<>("의약품이 저장되었습니다.");
    }
}
