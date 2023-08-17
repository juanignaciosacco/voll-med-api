package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query(value = """
            select m
            from Medico m
            where m.activo=true
            and
            m.especialidad=:especialidad
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.data=:fecha
            )
            order by rand()
            limit 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);

    @Query(value = """
            select p.activo
            from Medico p
            where p.id=:idMedico
            """)
    Boolean findActivoById(Long aLong);
}
