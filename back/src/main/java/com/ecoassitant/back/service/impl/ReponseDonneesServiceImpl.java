package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.resultat.ReponseDonneesDto;
import com.ecoassitant.back.dto.resultat.ReponseDto;
import com.ecoassitant.back.entity.ProjetEntity;
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
import java.util.List;
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
     *
     * @param reponseDonneeRepository   the ReponseDonneeRepository
     * @param reponsePossibleRepository the ReponsePossibleRepository
     * @param projetRepository          the ProjetRepository
     * @param questionRepository        the QuestionRepository
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
            return false;
            //throw new IllegalArgumentException();
        }
        var list = responses.getReponses();
        boolean result = true;
        Iterator<ReponseDto> reponseDtoIterator = list.iterator();

        while (result && reponseDtoIterator.hasNext()) {
            var reponseDto = reponseDtoIterator.next();
            var question = questionRepository.findById(reponseDto.getQuestionId());
            if (question.isEmpty()) {
                result = false;
                break;
            }
            var reponseDonnee = reponseDonneeRepository.findByReponseDonneeKeyQuestionAndReponseDonneeKeyProjet(question.get(), project.get());
            if (reponseDonnee.isPresent())
                reponseDonneeRepository.delete(reponseDonnee.get());
            if (!Objects.equals(reponseDto.getEntry(), "")){
                var reponseEntity = new ReponseDonneeEntity();
                var responseKey = new ReponseDonneeKey();

                responseKey.setProjet(project.get());
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
                reponseDonneeRepository.save(reponseEntity);
            }
        }
        return result;
    }

    @Override
    public void saveResponseDonnees(List<ReponseDonneeEntity> responses) {
        reponseDonneeRepository.saveAll(responses);
    }

    /**
     * save responseDonneeEntity in BDD
     *
     * @param answer ResponseDonnee
     * @return the ResponseDonnee save
     */
    @Override
    public ReponseDonneeEntity saveResponse(ReponseDonneeEntity answer) {
        return reponseDonneeRepository.save(answer);
    }

    @Override
    public List<ReponseDonneeEntity> findReponsesByProject(ProjetEntity projet) {
        return reponseDonneeRepository.findByReponseDonneeKey_Projet(projet);
    }
}
