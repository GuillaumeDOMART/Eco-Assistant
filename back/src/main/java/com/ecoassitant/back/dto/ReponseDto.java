package com.ecoassitant.back.dto;

/**
 * A reponse of a question for a project
 */
public class ReponseDto {
    private Long reponsePosId;
    private int entry;

    public Long getReponsePosId() {
        return reponsePosId;
    }

    public void setReponsePosId(Long reponsePosId) {
        this.reponsePosId = reponsePosId;
    }

    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }
}
