package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.GivenAnswerEntity;
import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * repository of ReponseDonnee
 */
public interface ReponseDonneeRepository extends JpaRepository<GivenAnswerEntity, Long> {

    /**
     * give a list of ReponseDonne for a project
     *
     * @param projet project linked of the ReponseDonnee
     * @return list ReponseDonnee linked of project
     */
    List<GivenAnswerEntity> findByReponseDonneeKey_Projet(ProjectEntity projet);

    /**
     * find a reponsedonnee in the data
     * @param question question of the response
     * @param projet project of the response
     * @return optional reponse donnee with the response searched
     */
    Optional<GivenAnswerEntity> findByReponseDonneeKeyQuestionAndReponseDonneeKeyProjet(QuestionEntity question, ProjectEntity projet);
}
