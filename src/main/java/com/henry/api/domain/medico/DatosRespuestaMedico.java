package com.henry.api.domain.medico;

import com.henry.api.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(Long id, String nombre, String email, String telefono, String documento, DatosDireccion direccion) {
}
