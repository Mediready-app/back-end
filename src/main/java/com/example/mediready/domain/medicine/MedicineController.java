package com.example.mediready.domain.medicine;

import com.example.mediready.domain.medicine.dto.GetMedicineSearchReq;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping("/search")
    public List<Medicine> searchMedicines(@RequestBody GetMedicineSearchReq getMedicineSearchReq) {
        return medicineService.searchMedicineByName(getMedicineSearchReq.getKeyword());
    }
}