package com.example.mediready.domain.scheduleDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleDateRepository extends JpaRepository<ScheduleDate, Long> {

}
