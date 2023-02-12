package com.ecoassitant.back.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Resultat of calculs sort by phase for create graphique
 */
public class ResultatDto {
    private final List<CalculDto> planification = new ArrayList<>();
    private final List<CalculDto> developpement = new ArrayList<>();
    private final List<CalculDto> test = new ArrayList<>();
    private final List<CalculDto> deploiement = new ArrayList<>();
    private final List<CalculDto> maintenance = new ArrayList<>();

    /**
     * add a result of calcul in Planifiaction
     * @param calcul result of calcul
     */
    public void addPlanification(CalculDto calcul){
        planification.add(calcul);
    }

    /**
     * add a result of calcul in Developpement
     * @param calcul result of calcul
     */
    public void addDeveloppement(CalculDto calcul){
        developpement.add(calcul);
    }

    /**
     * add a result of calcul in Test
     * @param calcul result of calcul
     */
    public void addTest(CalculDto calcul){
        test.add(calcul);
    }

    /**
     * add a result of calcul in Deploiement
     * @param calcul result of calcul
     */
    public void addDeploiement(CalculDto calcul){
        deploiement.add(calcul);
    }

    /**
     * add a result of calcul in Maintenance
     * @param calcul result of calcul
     */
    public void addMaintenance(CalculDto calcul){
        maintenance.add(calcul);
    }

    public List<CalculDto> getPlanification() {
        return planification;
    }

    public List<CalculDto> getDeveloppement() {
        return developpement;
    }

    public List<CalculDto> getTest() {
        return test;
    }

    public List<CalculDto> getDeploiement() {
        return deploiement;
    }

    public List<CalculDto> getMaintenance() {
        return maintenance;
    }
}
