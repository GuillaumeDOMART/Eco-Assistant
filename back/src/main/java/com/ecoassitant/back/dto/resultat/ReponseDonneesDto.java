package com.ecoassitant.back.dto.resultat;

import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Reponses of a project
 */
@NoArgsConstructor
public class ReponseDonneesDto {
    private String projetId;
    private List<ReponseDto> reponses;

    public Integer getProjetId() {
        return Integer.valueOf(projetId);
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
