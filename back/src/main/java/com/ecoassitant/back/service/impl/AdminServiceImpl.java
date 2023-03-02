package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.BanDto;
import com.ecoassitant.back.repository.ProfilRepository;
import com.ecoassitant.back.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final ProfilRepository profilRepository;

    public AdminServiceImpl(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    @Override
    public boolean ban(BanDto banDto) {
        var profil = profilRepository.findById(banDto.getIdToBan());
        if(profil.isEmpty()){
            return false;
        }
        var profilValue = profil.get();
        profilValue.setIsAdmin(-3);
        profilRepository.save(profilValue);
        return true;
    }
}
