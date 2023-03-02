package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.resultat.ReponseDonneesDto;
import com.ecoassitant.back.dto.resultat.ReponseDto;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import com.ecoassitant.back.entity.ReponseDonneeKey;
import com.ecoassitant.back.entity.tools.TypeQ;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.repository.QuestionRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.repository.ReponsePossibleRepository;
import com.ecoassitant.back.service.ReponseDonneesService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Objects;

/**
 * Implementation of ReponseDonnees Service
 */
@Service
public class ReponseDonneesServiceImpl implements ReponseDonneesService {
    private final ReponseDonneeRepository reponseDonneeRepository;
    private final ReponsePossibleRepository reponsePossibleRepository;
    private final ProjetRepository projetRepository;
    private final QuestionRepository questionRepository;

    /**
     * Function to create ReponseDonneesServiceImpl with ReponseDonneeRepository, ReponsePossibleRepository, ProjetRepository and QuestionRepository
     * @param reponseDonneeRepository the ReponseDonneeRepository
     * @param reponsePossibleRepository the ReponsePossibleRepository
     * @param projetRepository the ProjetRepository
     * @param questionRepository the QuestionRepository
     */
    public ReponseDonneesServiceImpl(ReponseDonneeRepository reponseDonneeRepository, ReponsePossibleRepository reponsePossibleRepository, ProjetRepository projetRepository, QuestionRepository questionRepository) {
        this.reponseDonneeRepository = reponseDonneeRepository;
        this.reponsePossibleRepository = reponsePossibleRepository;
        this.projetRepository = projetRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean saveResponseDonnees(ReponseDonneesDto responses) {
        var project = projetRepository.findById(responses.getProjetId());
        if (project.isEmpty()) {
            System.out.println("4");
            return false;
        }
        var list = responses.getReponses();
        boolean result = true;
        Iterator<ReponseDto> reponseDtoIterator = list.iterator();

        while (result && reponseDtoIterator.hasNext()){
            var reponseDto = reponseDtoIterator.next();
            if (!Objects.equals(reponseDto.getEntry(), "")){
                var reponseEntity = new ReponseDonneeEntity();
                var responseKey = new ReponseDonneeKey();

                responseKey.setProjet(project.get());
                var question = questionRepository.findById(reponseDto.getQuestionId());
                if (question.isEmpty()) {
                    result = false;
                    break;
                }
                responseKey.setQuestion(question.get());
                var reponsePossibles = reponsePossibleRepository.findByQuestionAsso(question.get());
                if (reponsePossibles.isEmpty()) {
                    result = false;
                    break;
                }

                if (question.get().getTypeQ().equals(TypeQ.NUMERIC)) { //NUMERIC
                    reponseEntity.setReponsePos(reponsePossibles.get(0));
                    if (!Objects.equals(reponseDto.getEntry(), ""))
                        reponseEntity.setEntry(Integer.parseInt(reponseDto.getEntry()));
                } else { //TypeQ.QCM
                    var reponsePossible = reponsePossibles.stream()
                            .filter(reponse -> reponse.getIntitule().equals(reponseDto.getEntry())).findFirst();
                    if (reponsePossible.isEmpty()) {
                        // CA PETE ICI
                        result = false;
                        break;
                    }
                    reponseEntity.setReponsePos(reponsePossible.get());
                    reponseEntity.setEntry(1);
                }

                reponseEntity.setReponseDonneeKey(responseKey);
                reponseDonneeRepository.delete(reponseEntity);
                reponseDonneeRepository.save(reponseEntity);
            }
        }
        return result;
    }
}
