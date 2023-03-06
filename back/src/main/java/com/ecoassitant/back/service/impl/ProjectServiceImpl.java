package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.project.ProjectIdDto;
import com.ecoassitant.back.dto.project.ProjetDto;
import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.tools.Etat;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjectRepository;
import com.ecoassitant.back.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of a ProjetService
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProfilRepository profilRepository;

    /**
     * Constructor of ConstanteService
     *
     * @param projectRepository projetRepository composite for using ProjetService methods
     */
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public ProjetDto getProject(Integer id) {
        var projet = projectRepository.findByIdProjet(id);
        return new ProjetDto(projet);
    }

    @Override
    public List<ProjetDto> findProjectByProfilId(Integer id) {
        return projectRepository.findByProfil_IdProfil(id).stream().map(ProjetDto::new).toList();

    }

    @Override
    public List<ProjetDto> findAll() {
        return projectRepository.findAll().stream().map(ProjetDto::new).toList();
    }

    @Override
    public Optional<ProjectIdDto> finish(String mail, ProjectIdDto projetDto) {
        var profilEntityOptional = profilRepository.findByMail(mail);
        if (profilEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        var projet = projectRepository.findByIdProjet(projetDto.getId());
        var mailOwner = projet.getProfil().getMail();
        if (!mailOwner.equals(mail)) {
            return Optional.empty();
        }
        projet.setEtat(Etat.FINISH);
        var saveProject = projectRepository.save(projet);
        return Optional.of(new ProjectIdDto(saveProject.getIdProjet()));
    }


    @Override
    public Optional<ProjectEntity> save(ProjectEntity projet) {
        return Optional.of(projectRepository.save(projet));
    }
}
