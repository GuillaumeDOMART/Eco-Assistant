package com.ecoassitant.back.dto.resultat;

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
    private  final List<CalculDto> horsPhase = new ArrayList<>();

    private  Double durationPlanification;
    private  Double durationDeveloppement;
    private  Double durationTest;
    private  Double durationDeploiement;
    private  Double durationMaintenance;
    //private  Double durationHorsPhase;


    /**
     * add a result of calcul in Planifiaction unless the planning is empty, we add its duration
     * @param calcul result of calcul
     */
    public void addPlanification(CalculDto calcul){
        if(planification.isEmpty())
            durationPlanification = calcul.getResult();
        else
            planification.add(calcul);
    }

    /**
     * add a result of calcul in Developpement unless the planning is empty, we add its duration
     * @param calcul result of calcul
     */
    public void addDeveloppement(CalculDto calcul){
        if (developpement.isEmpty())
            durationDeveloppement = calcul.getResult();
        else
            developpement.add(calcul);
    }

    /**
     * add a result of calcul in Test unless the planning is empty, we add its duration
     * @param calcul result of calcul
     */
    public void addTest(CalculDto calcul){
        if (test.isEmpty())
            durationTest = calcul.getResult();
        else
            test.add(calcul);
    }

    /**
     * add a result of calcul in Deploiement unless the planning is empty, we add its duration
     * @param calcul result of calcul
     */
    public void addDeploiement(CalculDto calcul){
        if (deploiement.isEmpty())
            durationDeploiement = calcul.getResult();
        else
            deploiement.add(calcul);
    }

    /**
     * add a result of calcul in Maintenance unless the planning is empty, we add its duration
     * @param calcul result of calcul
     */
    public void addMaintenance(CalculDto calcul){
        if (maintenance.isEmpty())
            durationMaintenance = calcul.getResult();
        else
            maintenance.add(calcul);
    }

    /**
     * add a result of calcul in Hors-Phase unless the planning is empty, we add its duration
     * @param calcul result of calcul
     */
    public void addHorsPhase(CalculDto calcul){
        horsPhase.add(calcul);
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

    public List<CalculDto> getHorsPhase() {
        return horsPhase;
    }

    public Double getDurationPlanification() {
        return durationPlanification;
    }

    public Double getDurationDeveloppement() {
        return durationDeveloppement;
    }

    public Double getDurationTest() {
        return durationTest;
    }

    public Double getDurationDeploiement() {
        return durationDeploiement;
    }

    public Double getDurationMaintenance() {
        return durationMaintenance;
    }
}
