package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ReponsePossible")
public class ReponsePossibleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReponsePos", nullable = false)
    private Long idReponsePos;

    @ManyToOne
    @JoinColumn(name = "questionAsso", nullable = false)
    private QuestionEntity questionAsso;

    @ManyToOne
    @JoinColumn(name = "questionSuiv", nullable = false)
    private QuestionEntity questionSuiv;

    @Column(name = "intitule")
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "constanteId", nullable = false)
    private ConstanteEntity constante;
}
