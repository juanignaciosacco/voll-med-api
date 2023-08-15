package med.voll.api.domain.paciente;

public record DatosListadoPacientes(
        Long id,

        String nombre,

        String documento_identidad,

        String email,

        String telefono
        ) {

    public DatosListadoPacientes(Paciente paciente) {
        this(paciente.getId(),paciente.getNombre(), paciente.getDocumento_identidad(), paciente.getEmail(), paciente.getTelefono());
    }
}
