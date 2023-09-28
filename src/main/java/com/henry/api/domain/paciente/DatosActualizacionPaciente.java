package com.henry.api.domain.paciente;

import com.henry.api.domain.direccion.DatosDireccion;
import jakarta.validation.Valid;

public record DatosActualizacionPaciente(Long id, String nombre, String telefono, @Valid DatosDireccion direccion) {
}