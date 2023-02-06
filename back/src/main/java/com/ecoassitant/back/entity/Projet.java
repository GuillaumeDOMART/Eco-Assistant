package com.ecoassitant.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Projet")

public class Projet {
    @Id
    private Long idProjet;

    private Long profilId;

    private String nomProjet;

    private Boolean state;
}
