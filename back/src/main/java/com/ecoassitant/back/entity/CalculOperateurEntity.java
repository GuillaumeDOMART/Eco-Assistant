package com.ecoassitant.back.entity;

import com.ecoassitant.back.entity.tools.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CalculOperateur")
public class CalculOperateurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCalculOp", nullable = false)
    private Long idCalculOp;

    @Enumerated(EnumType.STRING)
    @Column(name = "operateur")
    private Operator operateur;
}
