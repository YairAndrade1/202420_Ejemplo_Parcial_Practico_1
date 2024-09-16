package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({MedicoEspecialidadService.class, MedicoService.class, EspecialidadService.class})
public class MedicoEspecialidadServiceTest {

    @Autowired 
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private EntityManager entityManager;

    private PodamFactory factory;

    @BeforeEach
    void setUp(){
        entityManager.clear();
        factory = new PodamFactoryImpl();
    }

    @Test
    void addEspecialidad() throws EntityNotFoundException {
    
        MedicoEntity nuevoMedico = factory.manufacturePojo(MedicoEntity.class);
        nuevoMedico.setRegistroMedico("RM12345");
        entityManager.persist(nuevoMedico);
    
        EspecialidadEntity nuevaEspecialidad = factory.manufacturePojo(EspecialidadEntity.class);
        nuevaEspecialidad.setDescripcion("Descripción válida de más de 10 caracteres");
        entityManager.persist(nuevaEspecialidad);
        
        // Asociar la especialidad al médico
        MedicoEntity medicoActualizado = medicoEspecialidadService.addEspecialidad(nuevoMedico.getId(), nuevaEspecialidad.getId());
    
    }
    

    @Test
    void addEspecialidadMedicoNoExistente() {

        EspecialidadEntity nuevaEspecialidad = factory.manufacturePojo(EspecialidadEntity.class);
        nuevaEspecialidad.setDescripcion("Descripción válida de más de 10 caracteres");
        entityManager.persist(nuevaEspecialidad);

        Long medicoIdInexistente = 999L; // ID que no existe en la base de datos

        assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(medicoIdInexistente, nuevaEspecialidad.getId());
        });
    }

    @Test
    void addEspecialidadEspecialidadNoExistente() {

        MedicoEntity nuevoMedico = factory.manufacturePojo(MedicoEntity.class);
        nuevoMedico.setRegistroMedico("RM12345");
        entityManager.persist(nuevoMedico);

        Long especialidadIdInexistente = 999L; // ID que no existe en la base de datos

        assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(nuevoMedico.getId(), especialidadIdInexistente);
        });
    }
}
