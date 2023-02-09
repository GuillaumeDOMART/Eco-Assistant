package com.ecoassitant.back.dto;

/**
 * A reponse of a question for a project
 */
public class ReponseDto {
    private Long idReponsePossible;
    private int entry;

    public Long getIdReponsePossible() {
        return idReponsePossible;
    }

    public void setIdReponsePossible(Long idReponsePossible) {
        this.idReponsePossible = idReponsePossible;
    }

    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }
}
