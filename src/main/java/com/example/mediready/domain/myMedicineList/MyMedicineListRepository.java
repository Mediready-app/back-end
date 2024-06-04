package com.example.mediready.domain.myMedicineList;

import com.example.mediready.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyMedicineListRepository extends JpaRepository<MyMedicineList, Long> {

    boolean existsByUserIdAndMedicineId(Long userId, int medicineId);

    List<MyMedicineList> findByUserIdAndFolderId(Long userId, Long folderId);
}
