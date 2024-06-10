package com.example.mediready.domain.pharmacist;

import com.example.mediready.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

    Pharmacist findByUser(User user);
}