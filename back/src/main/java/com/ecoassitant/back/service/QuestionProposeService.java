package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.QuestionProposeDto;

import java.util.List;

public interface QuestionProposeService {
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
}
