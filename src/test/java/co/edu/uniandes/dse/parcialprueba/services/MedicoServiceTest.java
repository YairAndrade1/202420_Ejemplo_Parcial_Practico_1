package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Import(MedicoService.class)
@Transactional
public class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        entityManager.clear();
    }

    @Test
    void CreateMedico() throws IllegalOperationException {
        MedicoEntity nuevoMedico = factory.manufacturePojo(MedicoEntity.class);
        nuevoMedico.setRegistroMedico("RM5678");

        MedicoEntity result = medicoService.createMedico(nuevoMedico);
        assertNotNull(result);

        MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());
        assertEquals(nuevoMedico.getRegistroMedico(), entity.getRegistroMedico());
    }

    @Test
    void CreateMedicoRegistroInvalido() {
        MedicoEntity nuevoMedico = factory.manufacturePojo(MedicoEntity.class);
        nuevoMedico.setRegistroMedico("AA1234");

        Exception exception = assertThrows(IllegalOperationException.class, () -> {
            medicoService.createMedico(nuevoMedico);
        });

        String expectedMessage = "El registro médico debe comenzar con 'RM'.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
