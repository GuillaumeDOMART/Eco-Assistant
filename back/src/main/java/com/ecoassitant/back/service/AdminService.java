package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.BanDto;
import com.ecoassitant.back.dto.profil.ProfilIdDto;
import com.ecoassitant.back.dto.project.ProjetDto;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    boolean ban(BanDto banDto);

    Optional<List<ProjetDto>> getProjectsFinishedFromUserId(String mail, Integer profilId);
}
