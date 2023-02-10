package com.ecoassitant.back.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Resultat of calculs sort by phase for create graphique
 */
public class ResultatDto {
    private final List<Double> planification = new ArrayList<>();
    private final List<Double> developpement = new ArrayList<>();
    private final List<Double> test = new ArrayList<>();
    private final List<Double> deploiement = new ArrayList<>();
    private final List<Double> maintenance = new ArrayList<>();

    /**
     * add a result of calcul in Planifiaction
     * @param value result of calcul
     */
    public void addPlanification(Double value){
        planification.add(value);
    }

    /**
     * add a result of calcul in Developpement
     * @param value result of calcul
     */
    public void addDeveloppement(Double value){
        developpement.add(value);
    }

    /**
     * add a result of calcul in Test
     * @param value result of calcul
     */
    public void addTest(Double value){
        test.add(value);
    }

    /**
     * add a result of calcul in Deploiement
     * @param value result of calcul
     */
    public void addDeploiement(Double value){
        deploiement.add(value);
    }

    /**
     * add a result of calcul in Maintenance
     * @param value result of calcul
     */
    public void addMaintenance(Double value){
        maintenance.add(value);
    }
    public List<Double> getPlanification() {
        return planification;
    }

    public List<Double> getDeveloppement() {
        return developpement;
    }

    public List<Double> getTest() {
        return test;
    }

    public List<Double> getDeploiement() {
        return deploiement;
    }

    public List<Double> getMaintenance() {
        return maintenance;
    }
}
