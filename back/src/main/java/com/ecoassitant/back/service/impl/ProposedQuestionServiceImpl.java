package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dao.QuestionProposeDao;
import com.ecoassitant.back.dto.QuestionProposeDto;
import com.ecoassitant.back.entity.QuestionProposeEntity;
import com.ecoassitant.back.repository.QuestionProposeRepository;
import com.ecoassitant.back.service.ProposedQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation for QuestionProposeService
 */
@Service
public class ProposedQuestionServiceImpl implements ProposedQuestionService {

    private final QuestionProposeRepository repository;

    /**
     * Constructor of ConstanteService
     * @param repository A repository for QuestionPropose
     */
    @Autowired
    public ProposedQuestionServiceImpl(QuestionProposeRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuestionProposeDto getQuestionProposeByID(Integer id) {
        var qp = repository.findById(Long.parseLong(id.toString()));
        return qp.map(QuestionProposeDto::new).orElse(null);
    }

    @Override
    public List<QuestionProposeDto> findAll() {
        return repository.findAll().stream().map(QuestionProposeDto::new).toList();
    }

    @Override
    public Integer saveQuestionPropose(QuestionProposeDao qpdao) {
        var entity = new QuestionProposeEntity();
        entity.setVote(0);
        entity.setIntitule(qpdao.getIntitule());
        entity.setApprove(false);
        entity.setPhase(qpdao.getPhase());

        var saved =  repository.save(entity);
        return Math.toIntExact(saved.getIdQuestion());
    }
}
