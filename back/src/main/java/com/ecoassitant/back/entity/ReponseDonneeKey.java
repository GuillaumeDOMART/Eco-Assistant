package com.ecoassitant.back.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * primary key of ReponseDonnee
 */
@Embeddable
public class ReponseDonneeKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "projetid", nullable = false)
    private ProjetEntity projet;

    @ManyToOne
    @JoinColumn(name = "reponseposid", nullable = false)
    private ReponsePossibleEntity reponsePos;

    public ProjetEntity getProjet() {
        return projet;
    }

    public ReponsePossibleEntity getReponsePos() {
        return reponsePos;
    }

    public void setProjet(ProjetEntity projet) {
        this.projet = projet;
    }

    public void setReponsePos(ReponsePossibleEntity reponsePos) {
        this.reponsePos = reponsePos;
    }
}
