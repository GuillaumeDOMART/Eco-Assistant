package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.ProjetDto;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetServiceImpl implements ProjetService {
    private final ProjetRepository projetRepository;

    @Autowired
    public ProjetServiceImpl(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }


    @Override
    public ProjetDto getProject(Long id) {
        var projet = projetRepository.findByIdProjet(id);
        return new ProjetDto(projet);
    }

    @Override
    public List<ProjetDto> findProjectByProfilId(Long id) {
        var projets = projetRepository.findByProfil_IdProfil(id).stream().map(ProjetDto::new).toList();
        return projets;

    }

    @Override
    public List<ProjetDto> findAll() {
        var projets = projetRepository.findAll().stream().map(ProjetDto::new).toList();
        return projets;
    }
}
