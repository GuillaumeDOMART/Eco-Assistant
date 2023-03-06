package com.ecoassitant.back.service;

import com.ecoassitant.back.dao.QuestionProposeDao;
import com.ecoassitant.back.dto.QuestionProposeDto;

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
    QuestionProposeDto getQuestionProposeByID(Integer id);

    /**
     * Get List<QuestionProposeDto>in the DB
     * @return List<QuestionProposeDto>
     */
    List<QuestionProposeDto> findAll();

    /**
     * Save a Question Proposé (proposition)
     * @param qpdao A question proposée
     * @return the ID of thr newly created resources if save in bdd is successed, -1 otherwhise
     */
    Integer saveQuestionPropose(QuestionProposeDao qpdao);

}
