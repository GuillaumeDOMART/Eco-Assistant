package com.ecoassitant.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reponsedonnee")
public class ReponseDonneeEntity implements Serializable {

    @EmbeddedId
    private ReponseDonneeKey reponseDonneeKey;

    @Column(name = "entry", nullable = false)
    private int entry;
}

