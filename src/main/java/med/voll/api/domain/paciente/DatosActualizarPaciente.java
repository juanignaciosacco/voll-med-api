package med.voll.api.domain.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizarPaciente(

        @NotNull
        Long id,

        @NotBlank
        String nombre,

        String documento_identidad,

        @Email
        String email,

        String telefono,

        DatosDireccion direccion
) { }
