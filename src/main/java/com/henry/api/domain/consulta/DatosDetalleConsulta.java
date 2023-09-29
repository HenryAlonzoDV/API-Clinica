package com.henry.api.domain.consulta;

import java.time.LocalDateTime;

public record DatosDetalleConsulta(Long id, Long idPaciente, Long idMedico, LocalDateTime fecha) {
}
