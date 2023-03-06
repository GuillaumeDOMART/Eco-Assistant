package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dao.ProposedQuestionDao;
import com.ecoassitant.back.dto.ProposedQuestionDto;
import com.ecoassitant.back.entity.ProposedQuestionEntity;
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
    public ProposedQuestionDto getQuestionProposeByID(Integer id) {
        var qp = repository.findById(Long.parseLong(id.toString()));
        return qp.map(ProposedQuestionDto::new).orElse(null);
    }

    @Override
    public List<ProposedQuestionDto> findAll() {
        return repository.findAll().stream().map(ProposedQuestionDto::new).toList();
    }

    @Override
    public Integer saveQuestionPropose(ProposedQuestionDao qpdao) {
        var entity = new ProposedQuestionEntity();
        entity.setVote(0);
        entity.setEntitled(qpdao.getEntitled());
        entity.setApprove(false);
        entity.setPhase(qpdao.getPhase());

        var saved =  repository.save(entity);
        return Math.toIntExact(saved.getIdQuestion());
    }
}
