package com.henry.api.domain.consulta;

import com.henry.api.domain.medico.Especialidad;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosAgendaConsulta(Long id,
                                  @NotNull Long idPaciente,
                                  Long idMedico,
                                  @NotNull @Future LocalDateTime fecha,
                                  Especialidad especialidad) {
}
