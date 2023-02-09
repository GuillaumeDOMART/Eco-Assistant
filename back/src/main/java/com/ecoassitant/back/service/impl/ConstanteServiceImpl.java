package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.ConstanteDto;
import com.ecoassitant.back.repository.ConstantRepository;
import com.ecoassitant.back.service.ConstanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstanteServiceImpl implements ConstanteService {
    private final ConstantRepository constanteRepo;

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
        var constantes = constanteRepo.findAll().stream().map(ConstanteDto::new).toList();
        return constantes;
    }

}
