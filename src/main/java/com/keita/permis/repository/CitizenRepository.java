package com.keita.permis.repository;

import com.keita.permis.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen,Long> {
    boolean existsByEmail(String email);
    Optional<Citizen> findByEmail(String email);
    boolean existsByEmailAndFirstNameAndLastName(String email,String firstName,String password);
}
