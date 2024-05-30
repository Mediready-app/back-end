package com.example.mediready.domain.medicine;

import java.util.List;

public interface MedicineRepositoryCustom {
    List<Medicine> findMedicineByNameContaining(String keyword);
}
