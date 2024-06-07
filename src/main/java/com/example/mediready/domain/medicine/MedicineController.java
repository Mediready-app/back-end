package com.example.mediready.domain.medicine;

import com.example.mediready.domain.medicine.dto.GetMedicineDurInfoRes;
import com.example.mediready.domain.medicine.dto.GetMedicineInfoRes;
import com.example.mediready.domain.medicine.dto.GetMedicineSearchReq;
import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.BaseResponse;
import com.example.mediready.global.config.auth.AuthUser;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping("/search")
    public BaseResponse<List<Medicine>> searchMedicines(
        @RequestBody GetMedicineSearchReq getMedicineSearchReq) {
        return new BaseResponse<>("의약품 검색 결과입니다.",
            medicineService.searchMedicineByName(getMedicineSearchReq.getKeyword()));
    }

    @GetMapping
    public BaseResponse<GetMedicineInfoRes> getMedicineInfo(@RequestParam Long id)
        throws Exception {
        return new BaseResponse<>("의약품 정보입니다.",
            medicineService.getMedicineInfo(id));
    }

    @GetMapping("/dur")
    public BaseResponse<Map<String, List<GetMedicineDurInfoRes>>> getMedicineDurInfo(@AuthUser User user,
        @RequestParam int id) {
        return new BaseResponse<>("사용자의 보관된 의약품과의 병용 가능 여부 정보입니다.",
            medicineService.getMedicineDurInfo(user, id));
    }

}