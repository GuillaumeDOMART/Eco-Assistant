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
@Table(name = "constante")
public class ConstantEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconstante", nullable = false)
    private Long idConstante;

    @Column(name = "constante", nullable = false)
    private double constant;

    @Column(name = "tracabilite")
    private String source;
}
