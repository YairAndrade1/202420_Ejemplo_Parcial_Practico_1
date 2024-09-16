package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;

@Service
public class MedicoEspecialidadService {
    
    @Autowired 
    MedicoRepository medicoRepository;

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Transactional
    public MedicoEntity addEspecialidad( Long MedicoId, Long EspecialidadId) throws EntityNotFoundException{

        Optional <MedicoEntity> medicoEntity = medicoRepository.findById(MedicoId);
        Optional <EspecialidadEntity> especialidadEntity = especialidadRepository.findById(EspecialidadId);
        
        if (especialidadEntity.isEmpty()){
            throw new EntityNotFoundException("La especialidad no existe");
        }
        
        if (medicoEntity.isEmpty()){
            throw new EntityNotFoundException("El médico no existe");
        }
        
        MedicoEntity medico = medicoEntity.get();
        EspecialidadEntity especialidad = especialidadEntity.get();

        medico.getEspecialidades().add(especialidad);
        medicoRepository.save(medico);

        return medico;  
    }
    
}
