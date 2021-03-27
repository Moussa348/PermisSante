package com.keita.permis.repository;

import com.keita.permis.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen,Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
    boolean existsByEmailAndFirstNameAndLastName(String email,String firstName,String password);
    Optional<Citizen> findByEmailAndPassword(String email,String password);
    Optional<Citizen> findByEmailAndFirstNameAndLastName(String email,String firstName,String lastName);
}
