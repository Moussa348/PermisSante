package com.keita.permis.repository;

import com.keita.permis.model.PermitVaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermitVaccineRepository extends JpaRepository<PermitVaccine,Long> {

}
