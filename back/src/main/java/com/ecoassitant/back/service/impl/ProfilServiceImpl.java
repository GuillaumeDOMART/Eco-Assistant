package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.ProfilDto;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of ProfilService
 */
@Service
public class ProfilServiceImpl implements ProfilService {
    private final ProfilRepository repository;

    /**
     * Default constructor for ProfilServiceImpl
     */
    @Autowired
    public  ProfilServiceImpl(ProfilRepository repository){
        this.repository = repository;
    }

    @Override
    public ProfilDto getProfilByID(Long id){
        var profil = repository.findById(id);
        return profil.isEmpty() ? null : new ProfilDto(profil.get());
    }


    @Override
    public ProfilDto getProfilByMail(String mail) {

        var profil = repository.findByMail(mail);
        return profil == null ? null : new ProfilDto(profil);
    }
}
