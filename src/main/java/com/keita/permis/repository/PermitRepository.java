package com.keita.permis.repository;

import com.keita.permis.model.Permit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface PermitRepository extends JpaRepository<Permit,Long> {
    Optional<Permit> findByCitizenEmailAndCitizenPassword(String email,String password);
    Optional<Permit> findByCitizenEmailAndCitizenCellNumberAndCitizenCity(String email,String cellNumber,String city);
    Optional<Permit> findByActiveTrueAndCitizenEmail(String email);
    int countByCitizenEmail(String email);
}
