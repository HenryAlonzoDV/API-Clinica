package com.henry.api.paciente;

import com.henry.api.direccion.DatosDireccion;
import jakarta.validation.Valid;

public record DatosActualizacionPaciente(Long id, String nombre, String telefono, @Valid DatosDireccion direccion) {
}