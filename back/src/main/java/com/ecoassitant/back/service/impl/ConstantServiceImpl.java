package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.ConstantDto;
import com.ecoassitant.back.repository.ConstantRepository;
import com.ecoassitant.back.service.ConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of a ConstantService
 */
@Service
public class ConstantServiceImpl implements ConstantService {
    private final ConstantRepository constanteRepo;

    /**
     * Constructor of ConstantService
     * @param constanteRepo constanteRepository composite for using ConstantService methods
     */
    @Autowired
    public ConstantServiceImpl(ConstantRepository constanteRepo) {
        this.constanteRepo = constanteRepo;
    }


    @Override
    public ConstantDto getConstante(Long constanteId) {
        var constante = constanteRepo.findByIdConstante(constanteId);
        return new ConstantDto(constante);

    }

    @Override
    public List<ConstantDto> findAll() {
        return constanteRepo.findAll().stream().map(ConstantDto::new).toList();
    }

}
