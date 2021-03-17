package com.keita.permis.repository;

import com.keita.permis.model.Permit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PermitRepository extends JpaRepository<Permit,Long> {
    Optional<Permit> findByCitizenEmail(String email);
}
