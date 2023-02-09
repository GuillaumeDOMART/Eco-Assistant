package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ProfilDto;

public interface ProfilService {
    public ProfilDto getProfilByID(Long id);
    public ProfilDto getProfilByMail(String mail);
}
