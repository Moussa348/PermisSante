package com.keita.permis.repository;

import com.keita.permis.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen,Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndFirstNameAndLastName(String email,String firstName,String password);
}
