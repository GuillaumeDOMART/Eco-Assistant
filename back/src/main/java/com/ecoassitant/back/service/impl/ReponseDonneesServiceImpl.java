package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.ReponseDonneesDto;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import com.ecoassitant.back.entity.ReponseDonneeKey;
import com.ecoassitant.back.entity.tools.TypeQ;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.repository.QuestionRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.repository.ReponsePossibleRepository;
import com.ecoassitant.back.service.ReponseDonneesService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implementation of ReponseDonnees Service
 */
@Service
public class ReponseDonneesServiceImpl implements ReponseDonneesService {
    private final ReponseDonneeRepository reponseDonneeRepository;
    private final ReponsePossibleRepository reponsePossibleRepository;
    private final ProjetRepository projetRepository;
    private final QuestionRepository questionRepository;

    public ReponseDonneesServiceImpl(ReponseDonneeRepository reponseDonneeRepository, ReponsePossibleRepository reponsePossibleRepository, ProjetRepository projetRepository, QuestionRepository questionRepository) {
        this.reponseDonneeRepository = reponseDonneeRepository;
        this.reponsePossibleRepository = reponsePossibleRepository;
        this.projetRepository = projetRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean saveResponseDonnees(ReponseDonneesDto responses) {
        var project = projetRepository.findById(responses.getProjetId());
        if (project.isEmpty())
            return false;
            //throw new IllegalArgumentException();

        var list = responses.getReponses();
        AtomicBoolean result = new AtomicBoolean(true);
        list.forEach(reponseDto -> {
            var reponseEntity = new ReponseDonneeEntity();
            var responseKey = new ReponseDonneeKey();

            responseKey.setProjet(project.get());
            var question = questionRepository.findById(reponseDto.getQuestionId());
            if (question.isEmpty()){
                result.set(false);
                return;
            }
            var reponsePossibles = reponsePossibleRepository.findByQuestionAsso(question.get());
            if (reponsePossibles.isEmpty()) {
                result.set(false);
                return;
                //throw new IllegalArgumentException();
            }

            if (question.get().getTypeQ().equals(TypeQ.NUMERIC)) {
                responseKey.setReponsePos(reponsePossibles.get(0));
                reponseEntity.setEntry(Integer.parseInt(reponseDto.getEntry()));
            }
            else { //TypeQ.QCM
                var reponsePossible = reponsePossibles.stream()
                        .filter( reponse -> reponse.getIntitule().equals(reponseDto.getEntry())).findFirst();
                if (reponsePossible.isEmpty()){
                    result.set(false);
                    return;
                }
                responseKey.setReponsePos(reponsePossible.get());
                reponseEntity.setEntry(1);
            }

            reponseEntity.setReponseDonneeKey(responseKey);

            reponseDonneeRepository.save(reponseEntity);
        });
        return result.get();
    }
}
