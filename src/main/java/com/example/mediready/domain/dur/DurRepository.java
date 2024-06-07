package com.example.mediready.domain.dur;

import com.example.mediready.domain.medicine.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DurRepository extends JpaRepository<Dur, Integer> {
    boolean existsByDurIdPk_Id1AndDurIdPk_Id2(Medicine id1, Medicine id2);

}
