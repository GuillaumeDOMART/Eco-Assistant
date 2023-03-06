package com.ecoassitant.back.dto.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Result of calculation sort by phase for create the graphic
 */
public class ResultDto {
    private final List<CalculDto> planning = new ArrayList<>();
    private final List<CalculDto> development = new ArrayList<>();
    private final List<CalculDto> test = new ArrayList<>();
    private final List<CalculDto> deployment = new ArrayList<>();
    private final List<CalculDto> maintenance = new ArrayList<>();
    private  final List<CalculDto> outPhase = new ArrayList<>();

    private  Double planningDuration;
    private  Double developmentDuration;
    private  Double testDuration;
    private  Double deploymentDuration;
    private  Double maintenanceDuration;


    /**
     * add a result of calculation in Planning unless the planning is empty, we add its duration
     * @param calc result of calculation
     */
    public void addPlanning(CalculDto calc){
        if(planningDuration == null)
            planningDuration = calc.getResult();
        else
            planning.add(calc);
    }

    /**
     * add a result of calculation in Development unless the planning is empty, we add its duration
     * @param calc result of calculation
     */
    public void addDevelopment(CalculDto calc){
        if (developmentDuration == null)
            developmentDuration = calc.getResult();
        else
            development.add(calc);
    }

    /**
     * add a result of calculation in Test unless the planning is empty, we add its duration
     * @param calc result of calculation
     */
    public void addTest(CalculDto calc){
        if (testDuration == null)
            testDuration = calc.getResult();
        else
            test.add(calc);
    }

    /**
     * add a result of calculation in Deployment unless the planning is empty, we add its duration
     * @param calc result of calculation
     */
    public void addDeployment(CalculDto calc){
        if (deploymentDuration == null)
            deploymentDuration = calc.getResult();
        else
            deployment.add(calc);
    }

    /**
     * add a result of calculation in Maintenance unless the planning is empty, we add its duration
     * @param calc result of calculation
     */
    public void addMaintenance(CalculDto calc){
        if (maintenanceDuration == null)
            maintenanceDuration = calc.getResult();
        else
            maintenance.add(calc);
    }

    /**
     * add a result of calculation in Hors-Phase unless the planning is empty, we add its duration
     * @param calc result of calculation
     */
    public void addOutPhase(CalculDto calc){
        outPhase.add(calc);
    }

    public List<CalculDto> getPlanning() {
        return planning;
    }

    public List<CalculDto> getDevelopment() {
        return development;
    }

    public List<CalculDto> getTest() {
        return test;
    }

    public List<CalculDto> getDeployment() {
        return deployment;
    }

    public List<CalculDto> getMaintenance() {
        return maintenance;
    }

    public List<CalculDto> getOutPhase() {
        return outPhase;
    }

    public Double getPlanningDuration() {
        return planningDuration;
    }

    public Double getDevelopmentDuration() {
        return developmentDuration;
    }

    public Double getTestDuration() {
        return testDuration;
    }

    public Double getDeploymentDuration() {
        return deploymentDuration;
    }

    public Double getMaintenanceDuration() {
        return maintenanceDuration;
    }
}
