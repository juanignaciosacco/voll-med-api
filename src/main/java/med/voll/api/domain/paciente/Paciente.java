package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nombre;
    private String documento_identidad;
    private String telefono;
    private Boolean activo;

    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this.nombre = datosRegistroPaciente.nombre();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
        this.email = datosRegistroPaciente.email();
        this.documento_identidad = datosRegistroPaciente.documento_identidad();
        this.telefono = datosRegistroPaciente.telefono();
        this.activo = true;
    }

    public void actualizarInformacion(DatosActualizarPaciente datosActualizarPaciente) {
        if (datosActualizarPaciente.nombre() != null) {
            this.nombre = datosActualizarPaciente.nombre();
        }
        if (datosActualizarPaciente.telefono() != null) {
            this.telefono = datosActualizarPaciente.telefono();
        }
        if (datosActualizarPaciente.email() != null) {
            this.email = datosActualizarPaciente.email();
        }
        if (datosActualizarPaciente.direccion() != null) {
           this.direccion = new Direccion(datosActualizarPaciente.direccion());
        }
    }

    public void eliminar() {
        this.activo = false;
    }
}
