package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.Phase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questionpropose")
public class QuestionProposeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idquestion", nullable = false)
    private Long idQuestion;

    @Column(name = "intitule", nullable = false)
    private String intitule;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase", nullable = false)
    private Phase phase;

    @Column(name = "vote")
    private int vote;

    @Column(name = "isapprove", nullable = false)
    @Type(type="boolean")
    private boolean isApprove;

}
