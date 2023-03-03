package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.project.ProjetDto;
import com.ecoassitant.back.entity.tools.Etat;
import com.ecoassitant.back.entity.tools.TypeP;
import com.ecoassitant.back.dto.admin.BanDto;
import com.ecoassitant.back.dto.admin.BanProjectDto;
import com.ecoassitant.back.exception.NoSuchElementInDataBaseException;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.repository.ProjetRepository;
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

    private final ProjetRepository projetRepository;

    /**
     * Constructor for AdminServiceImpl
     * @param profilRepository ProfilRepository
     * @param projetRepository ProjetRepository
     */
    public AdminServiceImpl(ProfilRepository profilRepository, ProjetRepository projetRepository) {
        this.profilRepository = profilRepository;
        this.projetRepository = projetRepository;
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
        var projects = projetRepository.findByProfil_IdProfil(banDto.getIdToBan());

        for(var project : projects){
            projetRepository.delete(project);
        }

        return true;
    }

    @Override
    public Boolean banProject(BanProjectDto banDto) {
        var project = projetRepository.findById(banDto.getIdProjectToBan());
        if(project.isEmpty()){
            throw new NoSuchElementInDataBaseException();
        }
        projetRepository.delete(project.get());
        return true;
    }

    @Override
    public Optional<List<ProjetDto>> getProjectsFinishedFromUserId(String mail, Integer profilId) {
        var optConnectedProfil = profilRepository.findByMail(mail);
        var selectedProfil = profilRepository.findById(profilId);
        System.out.println("toto");
        if(optConnectedProfil.isEmpty() || selectedProfil.isEmpty()){
            System.out.println("tata");
            throw new NoSuchElementInDataBaseException();
        }
        var selectedProjects = projetRepository.findByProfil_IdProfil(profilId);
        return Optional.of(selectedProjects.stream().filter(p->p.getEtat().equals(Etat.FINISH) && p.getType().equals(TypeP.PROJET)).map(ProjetDto::new).toList());
    }
}
