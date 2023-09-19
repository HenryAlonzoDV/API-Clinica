package com.henry.api.medico;

public record DatosListadoMedico(Long id, String nombre, String especialidad, String email, String documento) {

    public DatosListadoMedico(Medico medico) {
        this(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(), medico.getEmail(), medico.getDocumento());
    }

}
