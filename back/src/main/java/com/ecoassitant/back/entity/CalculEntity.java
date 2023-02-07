package com.ecoassitant.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class Entity of table Profil
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Profil")
public class CalculEntity implements Serializable {

    @EmbeddedId
    private CalculKey id;

    @Column(name = "nbCalcul", nullable = false)
    private int nbCalcul;
}

