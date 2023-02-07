package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.Categorie;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idQuestion", nullable = false)
    private Long idQuestion;

    @Column(name = "intitule", nullable = false)
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "questionPre")
    private QuestionEntity questionPre;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeQ", nullable = false)
    private TypeQ typeQ;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase")
    private Phase phase;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie")
    private Categorie categorie;

    @Column(name = "visibilite")
    @Type(type="boolean")
    private boolean visibilite;
}
