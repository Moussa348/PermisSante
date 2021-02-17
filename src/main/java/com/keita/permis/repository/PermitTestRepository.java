package com.keita.permis.repository;

import com.keita.permis.model.PermitTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermitTestRepository extends JpaRepository<PermitTest,Long> {
}
