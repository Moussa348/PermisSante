package com.keita.permis.repository;

import com.keita.permis.model.Permit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermitRepository extends JpaRepository<Permit,Long> {
}
