package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.ConstanteDto;
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
    public ConstanteDto getConstante(Long constanteId) {
        var constante = constanteRepo.findByIdConstante(constanteId);
        return new ConstanteDto(constante);

    }

    @Override
    public List<ConstanteDto> findAll() {
        return constanteRepo.findAll().stream().map(ConstanteDto::new).toList();
    }

}
