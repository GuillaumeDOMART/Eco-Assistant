package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.result.GivenAnswersDto;
import com.ecoassitant.back.dto.result.ResponseDto;
import com.ecoassitant.back.entity.GivenAnswerEntity;
import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.GivenAnswerKey;
import com.ecoassitant.back.entity.tools.TypeQ;
import com.ecoassitant.back.repository.GivenAnswerRepository;
import com.ecoassitant.back.repository.ProjectRepository;
import com.ecoassitant.back.repository.QuestionRepository;
import com.ecoassitant.back.repository.ResponsePossibleRepository;
import com.ecoassitant.back.service.GivenAnswerService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of GivenAnswer Service
 */
@Service
public class GivenAnswerServiceImpl implements GivenAnswerService {
    private final GivenAnswerRepository givenAnswerRepository;
    private final ResponsePossibleRepository responsePossibleRepository;
    private final ProjectRepository projectRepository;
    private final QuestionRepository questionRepository;

    /**
     * Function to create GivenAnswerServiceImpl with GivenAnswerRepository, ResponsePossibleRepository, ProjectRepository and QuestionRepository
     *
     * @param givenAnswerRepository   the GivenAnswerRepository
     * @param responsePossibleRepository the ResponsePossibleRepository
     * @param projectRepository          the ProjectRepository
     * @param questionRepository        the QuestionRepository
     */
    public GivenAnswerServiceImpl(GivenAnswerRepository givenAnswerRepository, ResponsePossibleRepository responsePossibleRepository, ProjectRepository projectRepository, QuestionRepository questionRepository) {
        this.givenAnswerRepository = givenAnswerRepository;
        this.responsePossibleRepository = responsePossibleRepository;
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
            var responseDto = reponseDtoIterator.next();
            var question = questionRepository.findById(responseDto.getQuestionId());
            if (question.isEmpty()) {
                result = false;
                break;
            }
            var givenAnswer = givenAnswerRepository.findByGivenAnswerKeyQuestionAndAndGivenAnswerKeyProject(question.get(), project.get());
            if (givenAnswer.isPresent())
                givenAnswerRepository.delete(givenAnswer.get());
            if (!Objects.equals(responseDto.getEntry(), "")){
                var responseEntity = new GivenAnswerEntity();
                var responseKey = new GivenAnswerKey();

                responseKey.setProject(project.get());
                responseKey.setQuestion(question.get());
                var responsePossibles = responsePossibleRepository.findByQuestionAsso(question.get());
                if (responsePossibles.isEmpty()) {
                    result = false;
                    break;
                }

                if (question.get().getTypeQ().equals(TypeQ.NUMERIC)) { //NUMERIC
                    responseEntity.setReponsePos(responsePossibles.get(0));
                    if (!Objects.equals(responseDto.getEntry(), ""))
                        responseEntity.setEntry(Integer.parseInt(responseDto.getEntry()));
                } else { //TypeQ.QCM
                    var responsePossible = responsePossibles.stream()
                            .filter(response -> response.getIntitule().equals(responseDto.getEntry())).findFirst();
                    if (responsePossible.isEmpty()) {
                        // CA PETE ICI
                        result = false;
                        break;
                    }
                    responseEntity.setReponsePos(responsePossible.get());
                    responseEntity.setEntry(1);
                }

                responseEntity.setGivenAnswerKey(responseKey);
                givenAnswerRepository.save(responseEntity);
            }
        }
        return result;
    }

    @Override
    public void saveResponseDonnees(List<GivenAnswerEntity> responses) {
        givenAnswerRepository.saveAll(responses);
    }

    @Override
    public List<GivenAnswerEntity> findReponsesByProject(ProjectEntity project) {
        return givenAnswerRepository.findByGivenAnswerKey_Project(project);
    }
}
