package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.QuestionProposeDto;
import com.ecoassitant.back.repository.QuestionProposeRepository;
import com.ecoassitant.back.service.QuestionProposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionProposeServiceImpl implements QuestionProposeService {

    private final QuestionProposeRepository repository;

    @Autowired
    public QuestionProposeServiceImpl(QuestionProposeRepository repository) {
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
}
