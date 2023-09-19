package com.henry.api.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepositoy extends JpaRepository<Medico, Long> {

    Page<Medico> findByActivoTrue(Pageable pageable);
}
