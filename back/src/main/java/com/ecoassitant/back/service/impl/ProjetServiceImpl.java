package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.project.ProjectIdDto;
import com.ecoassitant.back.dto.project.ProjetDto;
import com.ecoassitant.back.entity.ProjetEntity;
import com.ecoassitant.back.entity.tools.Etat;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of a ProjetService
 */
@Service
public class ProjetServiceImpl implements ProjetService {
    private final ProjetRepository projetRepository;
    private final ProfilRepository profilRepository;

    /**
     * Constructor of ConstanteService
     *
     * @param projetRepository projetRepository composite for using ProjetService methods
     */
    @Autowired
    public ProjetServiceImpl(ProjetRepository projetRepository, ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
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

    @Override
    public Optional<ProjectIdDto> finish(String mail, ProjectIdDto projetDto) {
        var profilEntityOptional = profilRepository.findByMail(mail);
        if (profilEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        var projet = projetRepository.findByIdProjet(projetDto.getId());

        var mailOwner = projet.getProfil().getMail();
        if (!mailOwner.equals(mail)) {
            return Optional.empty();
        }
        projet.setEtat(Etat.FINISH);
        var saveProject = projetRepository.save(projet);
        return Optional.of(new ProjectIdDto(saveProject.getIdProjet()));
    }

    @Override
    public boolean isFinish(Integer id) {
        try {
            return Etat.FINISH.equals(getProject(id).getEtat());
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public Optional<ProjetEntity> save(ProjetEntity projet) {
        return Optional.of(projetRepository.save(projet));
    }
}
