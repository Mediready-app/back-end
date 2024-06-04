package com.example.mediready.domain.myMedicineList;

import com.example.mediready.domain.myMedicineList.dto.ModifyMyMedicineReq;
import com.example.mediready.domain.myMedicineList.dto.AddMyMedicineReq;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.BaseResponse;
import com.example.mediready.global.config.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-medicine-list")
public class MyMedicineListController {

    private final MyMedicineListService myMedicineListService;

    @PostMapping
    public BaseResponse<String> addMyMedicine(@AuthUser User user,
        @RequestBody AddMyMedicineReq request) {
        myMedicineListService.addMyMedicine(user, request);
        return new BaseResponse<>("의약품이 저장되었습니다.");
    }

    @PatchMapping("/{id}")
    public BaseResponse<String> modifyMyMedicine(@AuthUser User user, @PathVariable Long id,
        @RequestBody ModifyMyMedicineReq request) {
        myMedicineListService.modifyMyMedicine(user, id, request);
        return new BaseResponse<>("의약품 보관 내용이 수정되었습니다.");
    }
}
