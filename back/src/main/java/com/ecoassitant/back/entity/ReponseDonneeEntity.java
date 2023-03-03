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

    /**
     * Update the project id related to this answer, used for the copy of the response to another project
     *
     * @param project Project about to be copied
     */
    public void updateReponseDonneeProjectId(ProjetEntity project) {
        this.reponseDonneeKey.setProjet(project);
    }
}

