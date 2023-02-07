package com.ecoassitant.back.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ReponseDonneeKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "projetId", nullable = false)
    private ProjetEntity projet;

    @ManyToOne
    @JoinColumn(name = "reponsePosId", nullable = false)
    private ReponsePossibleEntity reponsePos;
}
