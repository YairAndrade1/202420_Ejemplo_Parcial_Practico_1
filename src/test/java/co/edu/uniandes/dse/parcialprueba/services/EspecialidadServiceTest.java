package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@Transactional
@DataJpaTest
@Import(EspecialidadService.class)
public class EspecialidadServiceTest {

    @Autowired 
    private EspecialidadService especialidadService;

    @Autowired 
    private EspecialidadRepository especialidadRepository;

    @Autowired 
    private EntityManager entityManager; 

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void setUp(){

        entityManager.clear();

    }

    @Test
    void createEspecialidad() throws IllegalOperationException{
        EspecialidadEntity nuevaEspecialidad = factory.manufacturePojo(EspecialidadEntity.class);
        nuevaEspecialidad.setDescripcion("Esta descripcion es suficientemente larga");

        EspecialidadEntity resultado = especialidadService.createEspecialidad(nuevaEspecialidad);
        assertNotNull(resultado);

        EspecialidadEntity entity = entityManager.find(EspecialidadEntity.class, resultado.getId());
        assertEquals(nuevaEspecialidad.getDescripcion(), entity.getDescripcion());

    }

    @Test 
    void createEspecialidadIllegalOperation(){
        EspecialidadEntity nuevaEspecialidad = factory.manufacturePojo(EspecialidadEntity.class);
        nuevaEspecialidad.setDescripcion(" "); // especialidad de menos de 10 caracteres.

        Exception exception = assertThrows(IllegalOperationException.class, ()-> {
            especialidadService.createEspecialidad(nuevaEspecialidad);
        });

        String expectedMessage = "La descripción es muy corta, menor a 10 caracteres";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
