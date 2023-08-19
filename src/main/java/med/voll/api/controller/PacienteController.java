package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DatosListadoPacientes;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosActualizarPaciente;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.DatosRespuestaPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public ResponseEntity<Page<DatosListadoPacientes>> listarPacientes(@PageableDefault(size = 5) Pageable paginacion) {
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPacientes::new));
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrar(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente, UriComponentsBuilder uriComponentsBuilder) {
    Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
    DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),paciente.getTelefono(),paciente.getDocumento_identidad(),
            new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                    paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(),
                    paciente.getDireccion().getComplemento()));
    URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
    return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente) {
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarInformacion(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),paciente.getTelefono(),paciente.getDocumento_identidad(),
            new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                    paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(),
                    paciente.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.eliminar();
        return ResponseEntity.noContent().build();
    }
}