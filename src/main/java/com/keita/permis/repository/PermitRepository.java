package com.keita.permis.repository;

import com.keita.permis.model.Permit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface PermitRepository extends JpaRepository<Permit,Long> {
    Optional<Permit> findByActiveTrueAndCitizenEmail(String email);
    int countByCitizenEmail(String email);
    List<Permit> getByExpirationDateBefore(LocalDate date);

    @Transactional
    @Modifying
    @Query("UPDATE Permit p set p.active =false where p.expirationDate < :date")
    int disablePermit(@Param("date")LocalDate dateNow);
}
