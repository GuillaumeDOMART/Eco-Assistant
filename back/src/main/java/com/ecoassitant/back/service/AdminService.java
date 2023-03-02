package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.admin.BanDto;
import com.ecoassitant.back.dto.admin.BanProjectDto;

public interface AdminService {

    boolean ban(BanDto banDto);

    Boolean banProject(BanProjectDto banDto);
}
