package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.project.ProjectDto;
import com.ecoassitant.back.entity.tools.State;
import com.ecoassitant.back.entity.tools.TypeP;
import com.ecoassitant.back.dto.admin.BanDto;
import com.ecoassitant.back.dto.admin.BanProjectDto;
import com.ecoassitant.back.exception.NoSuchElementInDataBaseException;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjectRepository;
import com.ecoassitant.back.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * AdminService implementation
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final ProfilRepository profilRepository;

    private final ProjectRepository projectRepository;

    /**
     * Constructor for AdminServiceImpl
     * @param profilRepository ProfilRepository
     * @param projectRepository ProjetRepository
     */
    public AdminServiceImpl(ProfilRepository profilRepository, ProjectRepository projectRepository) {
        this.profilRepository = profilRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean ban(BanDto banDto) {
        var profil = profilRepository.findById(banDto.getIdToBan());
        var anonyme = profilRepository.findByMail("anonyme@demo.fr");
        if(profil.isEmpty() || anonyme.isEmpty()){
            throw new NoSuchElementInDataBaseException();
        }
        var profilValue = profil.get();
        profilValue.setIsAdmin(-3);
        profilRepository.save(profilValue);

        var anonymeValue = anonyme.get();
        var projects = projectRepository.findByProfil_IdProfil(banDto.getIdToBan());

        for(var project : projects){
            projectRepository.delete(project);
        }

        return true;
    }

    @Override
    public Boolean banProject(BanProjectDto banDto) {
        var project = projectRepository.findById(banDto.getIdProjectToBan());
        if(project.isEmpty()){
            throw new NoSuchElementInDataBaseException();
        }
        projectRepository.delete(project.get());
        return true;
    }

    @Override
    public Optional<List<ProjectDto>> getProjectsFinishedFromUserId(String mail, Integer profilId) {
        var optConnectedProfil = profilRepository.findByMail(mail);
        var selectedProfil = profilRepository.findById(profilId);
        System.out.println("toto");
        if(optConnectedProfil.isEmpty() || selectedProfil.isEmpty()){
            System.out.println("tata");
            throw new NoSuchElementInDataBaseException();
        }
        var selectedProjects = projectRepository.findByProfil_IdProfil(profilId);
        return Optional.of(selectedProjects.stream().filter(p->p.getState().equals(State.FINISH) && p.getType().equals(TypeP.PROJET)).map(ProjectDto::new).toList());
    }
}
