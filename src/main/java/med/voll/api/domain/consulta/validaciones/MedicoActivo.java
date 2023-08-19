package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas {

    @Autowired
    private MedicoRepository repository;

    @Override
    public void validar(DatosAgendarConsulta datos) {

        if (datos.idMedico() != null) {
            return;
        }

        // VERIFICAR ESTO QUE LA CONSOLA DICE QUE SIEMPRE ES NULL!!!
        var medicoActivo = repository.findActivoById(datos.idMedico());

        if (!medicoActivo) {
            throw new ValidationException("No se permite agendar citas con medicos inactivos en el sistema");
        }
    }
}
