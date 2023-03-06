package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.project.ProjectDto;
import com.ecoassitant.back.dto.project.ProjectIdDto;
import com.ecoassitant.back.entity.ProjectEntity;
import com.ecoassitant.back.entity.tools.State;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjectRepository;
import com.ecoassitant.back.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of a ProjectService
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProfilRepository profilRepository;

    /**
     * Constructor of ConstantService
     *
     * @param projectRepository projectRepository composite for using ProjectService methods
     */
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public ProjectDto getProject(Integer id) {
        var project = projectRepository.findByIdProject(id);
        return new ProjectDto(project);
    }

    @Override
    public List<ProjectDto> findProjectByProfilId(Integer id) {
        return projectRepository.findByProfil_IdProfil(id).stream().map(ProjectDto::new).toList();

    }

    @Override
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream().map(ProjectDto::new).toList();
    }

    @Override
    public Optional<ProjectIdDto> finish(String mail, ProjectIdDto projectIdDto) {
        var profilEntityOptional = profilRepository.findByMail(mail);
        if (profilEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        var project = projectRepository.findByIdProject(projectIdDto.getId());
        var mailOwner = project.getProfil().getMail();
        if (!mailOwner.equals(mail)) {
            return Optional.empty();
        }
        project.setState(State.FINI);
        var saveProject = projectRepository.save(project);
        return Optional.of(new ProjectIdDto(saveProject.getIdProject()));
    }


    @Override
    public Optional<ProjectEntity> save(ProjectEntity project) {
        return Optional.of(projectRepository.save(project));
    }
}
