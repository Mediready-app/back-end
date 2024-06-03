package com.example.mediready.domain.myMedicineList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyMedicineListRepository extends JpaRepository<MyMedicineList, Long> {
    boolean existsByUserIdAndMedicineId(Long userId, int medicineId);
}
