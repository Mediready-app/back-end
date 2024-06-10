package com.example.mediready.domain.scheduleDate;

import com.example.mediready.domain.user.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleDateRepository extends JpaRepository<ScheduleDate, Long> {

    List<ScheduleDate> findByUserAndStartDateGreaterThanEqual(User user, LocalDate today);
}
