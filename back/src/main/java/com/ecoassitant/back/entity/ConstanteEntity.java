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
@Table(name = "Constante")
public class ConstanteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConstante", nullable = false)
    private Long idConstante;

    @Column(name = "constante", nullable = false)
    private int constante;

    @Column(name = "tracabilite")
    private String tracabilite;
}
