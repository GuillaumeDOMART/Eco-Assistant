package com.ecoassitant.back.dto;

import java.util.ArrayList;
import java.util.List;

public class ResultatDto {
    private final List<Double> planification = new ArrayList<>();
    private final List<Double> developpement = new ArrayList<>();
    private final List<Double> test = new ArrayList<>();
    private final List<Double> deploiement = new ArrayList<>();
    private final List<Double> maintenance = new ArrayList<>();

    public void addPlanification(Double value){
        planification.add(value);
    }

    public void addDeveloppement(Double value){
        developpement.add(value);
    }

    public void addTest(Double value){
        test.add(value);
    }
    public void addDeploiement(Double value){
        deploiement.add(value);
    }
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
