package com.ecoassitant.back.dto;

import java.util.List;

/**
 * Reponses of a project
 */
public class ReponseDonneesDto {
    private Long idProject;
    private List<ReponseDto> reponses;

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public List<ReponseDto> getReponses() {
        return reponses;
    }

    public void setReponses(List<ReponseDto> reponses) {
        this.reponses = reponses;
    }
}
