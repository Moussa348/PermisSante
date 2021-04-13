package com.keita.permis.repository;

import com.keita.permis.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen,Long> {
    boolean existsByEmail(String email);
    Optional<Citizen> findByEmail(String email);
    Optional<Citizen> findBySocialInsurance(String socialInsurance);
    Optional<Citizen> findByEmailAndPassword(String email,String password);//TODO:will replace existByEmailAndPassword in AuthenticationService
    Optional<Citizen> findByEmailAndPasswordAndCellNumberAndCity(String email,String password, String cellNumber,String city);
}
