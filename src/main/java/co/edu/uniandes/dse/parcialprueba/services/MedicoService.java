package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;

@Service
public class MedicoService {
    
    @Autowired
    MedicoRepository medicoRepository;

    public MedicoEntity createMedico(MedicoEntity medico) throws IllegalOperationException {
        
        if(!medico.getRegistroMedico().startsWith("RM")){
            throw new IllegalOperationException("El registro médico debe comenzar con 'RM'.");
        }

    return medicoRepository.save(medico);

    }
}
