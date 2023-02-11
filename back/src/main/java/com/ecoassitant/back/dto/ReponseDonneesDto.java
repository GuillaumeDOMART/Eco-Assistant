package com.ecoassitant.back.dto;

import java.util.List;

/**
 * Reponses of a project
 */
public class ReponseDonneesDto {
    private String projetId;
    private List<ReponseDto> reponses;

    public Long getProjetId() {
        return Long.valueOf(projetId);
    }

    public void setProjetId(Long projetId) {
        this.projetId = projetId.toString();
    }

    public List<ReponseDto> getReponses() {
        return reponses;
    }

    public void setReponses(List<ReponseDto> reponses) {
        this.reponses = List.copyOf(reponses);
    }
}
