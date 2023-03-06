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
public class GivenAnswerEntity implements Serializable {

    @EmbeddedId
    private GivenAnswerKey givenAnswerKey;
    @OneToOne
    @JoinColumn(name = "reponseposid", nullable = false)
    private ResponsePossibleEntity reponsePos;

    @Column(name = "entry", nullable = false)
    private int entry;

    /**
     * Update the project id related to this answer, used for the copy of the response to another project
     *
     * @param project Project about to be copied
     */
    public void updateGivenAnswerProjectId(ProjectEntity project) {
        this.givenAnswerKey.setProjet(project);
    }
}

