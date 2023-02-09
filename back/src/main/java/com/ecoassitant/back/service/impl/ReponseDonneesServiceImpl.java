package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.ReponseDonneesDto;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import com.ecoassitant.back.entity.ReponseDonneeKey;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.repository.ReponsePossibleRepository;
import com.ecoassitant.back.service.ReponseDonneesService;
import org.springframework.stereotype.Service;

@Service
public class ReponseDonneesServiceImpl implements ReponseDonneesService {
    private final ReponseDonneeRepository reponseDonneeRepository;
    private final ReponsePossibleRepository reponsePossibleRepository;
    private final ProjetRepository projetRepository;

    public ReponseDonneesServiceImpl(ReponseDonneeRepository reponseDonneeRepository, ReponsePossibleRepository reponsePossibleRepository, ProjetRepository projetRepository) {
        this.reponseDonneeRepository = reponseDonneeRepository;
        this.reponsePossibleRepository = reponsePossibleRepository;
        this.projetRepository = projetRepository;
    }

    @Override
    public void saveResponseDonnees(ReponseDonneesDto responses) {
        var project = projetRepository.findById(responses.getIdProject());
        if (project.isEmpty())
            throw new IllegalArgumentException();

        var list = responses.getReponses();
        list.forEach(reponseDto -> {
            var responseKey = new ReponseDonneeKey();
            responseKey.setProjet(project.get());
            var reponsePossible = reponsePossibleRepository.findById(reponseDto.getIdReponsePossible());
            if (reponsePossible.isEmpty())
                throw new IllegalArgumentException();
            responseKey.setReponsePos(reponsePossible.get());

            var reponseEntity = new ReponseDonneeEntity();
            reponseEntity.setEntry(reponseDto.getEntry());
            reponseEntity.setReponseDonneeKey(responseKey);

            reponseDonneeRepository.save(reponseEntity);
        });

    }
}
