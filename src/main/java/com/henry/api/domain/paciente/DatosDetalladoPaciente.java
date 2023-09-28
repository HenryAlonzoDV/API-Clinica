package com.henry.api.domain.paciente;

import com.henry.api.domain.direccion.Direccion;

public record DatosDetalladoPaciente(Long id, String nombre, String email, String telefono, String documentoIdentidad, Direccion direccion) {
    public DatosDetalladoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoIdentidad(), paciente.getDireccion());
    }
}