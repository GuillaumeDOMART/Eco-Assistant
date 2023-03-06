package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.result.GivenAnswersDto;
import com.ecoassitant.back.dto.result.ResponseDto;
import com.ecoassitant.back.entity.GivenAnswerEntity;
import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.GivenAnswerKey;
import com.ecoassitant.back.entity.tools.TypeQ;
import com.ecoassitant.back.repository.ProjectRepository;
import com.ecoassitant.back.repository.QuestionRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.repository.ReponsePossibleRepository;
import com.ecoassitant.back.service.GivenAnswerService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of ReponseDonnees Service
 */
@Service
public class GivenAnswerServiceImpl implements GivenAnswerService {
    private final ReponseDonneeRepository reponseDonneeRepository;
    private final ReponsePossibleRepository reponsePossibleRepository;
    private final ProjectRepository projectRepository;
    private final QuestionRepository questionRepository;

    /**
     * Function to create ReponseDonneesServiceImpl with ReponseDonneeRepository, ReponsePossibleRepository, ProjetRepository and QuestionRepository
     *
     * @param reponseDonneeRepository   the ReponseDonneeRepository
     * @param reponsePossibleRepository the ReponsePossibleRepository
     * @param projectRepository          the ProjetRepository
     * @param questionRepository        the QuestionRepository
     */
    public GivenAnswerServiceImpl(ReponseDonneeRepository reponseDonneeRepository, ReponsePossibleRepository reponsePossibleRepository, ProjectRepository projectRepository, QuestionRepository questionRepository) {
        this.reponseDonneeRepository = reponseDonneeRepository;
        this.reponsePossibleRepository = reponsePossibleRepository;
        this.projectRepository = projectRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean saveResponseDonnees(GivenAnswersDto responses) {
        var project = projectRepository.findById(responses.getProjectId());
        if (project.isEmpty()) {
            return false;
            //throw new IllegalArgumentException();
        }
        var list = responses.getResponses();
        boolean result = true;
        Iterator<ResponseDto> reponseDtoIterator = list.iterator();

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
                var reponseEntity = new GivenAnswerEntity();
                var responseKey = new GivenAnswerKey();

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

                reponseEntity.setGivenAnswerKey(responseKey);
                reponseDonneeRepository.save(reponseEntity);
            }
        }
        return result;
    }

    @Override
    public void saveResponseDonnees(List<GivenAnswerEntity> responses) {
        reponseDonneeRepository.saveAll(responses);
    }

    @Override
    public List<GivenAnswerEntity> findReponsesByProject(ProjectEntity projet) {
        return reponseDonneeRepository.findByReponseDonneeKey_Projet(projet);
    }
}
