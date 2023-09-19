package com.henry.api.medico;

import com.henry.api.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UniqueElements;

public record DatosRegistroMedico (
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{8,10}")
        String telefono,
        @NotBlank
        @Pattern(regexp = "\\d{6,8}")  //esta anotacion para que solo acepte numeros entre 6 y 8 digitos
        String documento,
        @NotNull
        Especialidad especialidad,
        @NotNull
        @Valid// NOTNULL porque  DATOSDIRECCIONES es un objeto
        DatosDireccion direccion) {

}
