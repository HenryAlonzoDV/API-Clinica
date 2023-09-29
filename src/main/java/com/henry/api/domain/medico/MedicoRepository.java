package com.henry.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findByActivoTrue(Pageable pageable);

    /* seleccionamos todos los medicos activos,
    y correspondan con la especialidad pasada por parametro,
    ademas que su id no este dentro de los que ya tienen fechas asignadas (SUB CONSULTA)
    ultimo se ordena de forma aleatoria y limitamos el resultado a 1 solo medico
     */
    @Query("""
            SELECT m FROM medico m
            WHERE m.activo = 1 AND
            m.especialidad = :specialidad AND
            m.id NOT INT (
            SELECT c.medico.id FROM consulta c
            c.data = :fecha
            )
            ORDER BY RAND()
            LIMIT 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}
