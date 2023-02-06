package entity;

import entity.tools.Categorie;
import entity.tools.Phase;
import entity.tools.TypeQ;
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
    private String initule;

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
