package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.ConstantDto;
import com.ecoassitant.back.repository.ConstantRepository;
import com.ecoassitant.back.service.ConstanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of a ConstanteService
 */
@Service
public class ConstanteServiceImpl implements ConstanteService {
    private final ConstantRepository constanteRepo;

    /**
     * Constructor of ConstanteService
     * @param constanteRepo constanteRepository composite for using ConstanteService methods
     */
    @Autowired
    public ConstanteServiceImpl(ConstantRepository constanteRepo) {
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
