package com.example.mediready.domain.medicine;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public List<Medicine> searchMedicineByName(String keyword) {
        return medicineRepository.findMedicineByNameContaining(keyword);
    }
}