package com.keita.permis.repository;

import com.keita.permis.enums.PermitCategory;
import com.keita.permis.model.Permit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PermitRepository extends JpaRepository<Permit,Long> {
   List<Permit> findAllByDateBetween(Date firstDate,Date secondDate);
   List<Permit> findByPermitCategory(PermitCategory permitCategory);
}
