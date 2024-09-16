package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;

@Service
public class EspecialidadService {

    @Autowired 
    EspecialidadRepository especialidadRepository;

    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidad) throws IllegalOperationException {

        if(especialidad.getDescripcion().length() < 10){
            throw new IllegalOperationException("La descripción es muy corta, menor a 10 caracteres");
        }

    return especialidadRepository.save(especialidad);
    }
    
    
}
