package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.ProfilDto;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfilServiceImpl implements ProfilService {
    private final ProfilRepository repository;

    @Autowired
    public  ProfilServiceImpl(ProfilRepository repository){
        this.repository = repository;
    }

    public ProfilDto getProfilByID(Long id){
        return new ProfilDto(repository.findById(id).get());
    }

    @Override
    public ProfilDto getProfilByMail(String mail) {

        var profil = repository.findByMail(mail);
        return profil == null ? null : new ProfilDto(profil);
    }
}
