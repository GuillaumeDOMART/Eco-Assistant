package com.ecoassitant.back.service;

import com.ecoassitant.back.dao.ProposedQuestionDao;
import com.ecoassitant.back.dto.ProposedQuestionDto;

import java.util.List;

/**
 * Service for QuestionProposeController
 */
public interface ProposedQuestionService {
    /**
     * Retrieve a question proposée by it id
     * @param id the profil id
     * @return a ProfilDto with the submitted ID, or null if it doesn't exist
     */
    ProposedQuestionDto getQuestionProposeByID(Integer id);

    /**
     * Get List<ProposedQuestionDto>in the DB
     * @return List<ProposedQuestionDto>
     */
    List<ProposedQuestionDto> findAll();

    /**
     * Save a Question Proposé (proposition)
     * @param qpdao A question proposée
     * @return the ID of thr newly created resources if save in bdd is successed, -1 otherwhise
     */
    Integer saveQuestionPropose(ProposedQuestionDao qpdao);

}
