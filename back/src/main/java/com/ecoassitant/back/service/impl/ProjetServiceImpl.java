package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.project.ProjetDto;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of a ProjetService
 */
@Service
public class ProjetServiceImpl implements ProjetService {
    private final ProjetRepository projetRepository;

    /**
     * Constructor of ConstanteService
     * @param projetRepository projetRepository composite for using ProjetService methods
     */
    @Autowired
    public ProjetServiceImpl(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }


    @Override
    public ProjetDto getProject(Integer id) {
        var projet = projetRepository.findByIdProjet(id);
        return new ProjetDto(projet);
    }

    @Override
    public List<ProjetDto> findProjectByProfilId(Integer id) {
        return projetRepository.findByProfil_IdProfil(id).stream().map(ProjetDto::new).toList();

    }

    @Override
    public List<ProjetDto> findAll() {
        return projetRepository.findAll().stream().map(ProjetDto::new).toList();
    }
}
