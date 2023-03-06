package com.ecoassitant.back.calcul;

import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.entity.ConstanteEntity;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import com.ecoassitant.back.entity.ReponsePossibleEntity;
import com.ecoassitant.back.entity.tools.Phase;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ReformedOperation create with  parts of calculation(calculationEntity) and GivenAnswer for a project
 */
public class ReformedOperation {
    private final List<ReponsePossibleEntity> dependencies;

    private final String entitled;
    private final List<CalculEntity> calculus;
    private final List<ReponseDonneeEntity> repDon;
    private final Stack<OperationElem> stack = new Stack<>();
    private final Phase phase;

    /**
     * Constructor of ReformedOperation
     * @param calculus list of calculations recovery of the data
     * @param givAns list of GivenAnswer for a project
     */
    public ReformedOperation(List<CalculEntity> calculus, List<ReponseDonneeEntity> givAns){
        Objects.requireNonNull(calculus);
        this.calculus = List.copyOf(calculus);
        this.repDon = List.copyOf(givAns);
        dependencies = new ArrayList<>();
        phase = calculus.get(0).getPhase();
        calculus.forEach(calculEntity -> dependencies.add(calculEntity.getReponsePossible()));
        entitled = calculus.stream()
                .map(CalculEntity::getReponsePossible)
                .map(ReponsePossibleEntity::getConstante)
                .map(ConstanteEntity::getTracabilite)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Try to execute the calculation if it has all dependencies
     * @return the result of the calculation if possible
     */
    public Optional<Double> execute(){
        if(!isPossible())
            return Optional.empty();
        polishCalculator(join());
        var stack2 = new Stack<Double>();
        var stack3 = new Stack<OperationElem>();
        while (!stack.isEmpty())
            stack3.push(stack.pop());
        while (!stack3.isEmpty()){
            var op = stack3.pop();
            if(op.type().equals(TypeOp.OPERAND)) {
                Operand operand = (Operand) op;
                stack2.push(operand.val());
            }
            else {
                var y = stack2.pop();
                var x = stack2.pop();
                Operator operateur = (Operator) op;
                stack2.push(operateur.execute(x,y));
            }
        }
        return Optional.of(stack2.pop());
    }

    /**
     * check if the operation have all dependencies
     * @return true if all dependencies has a response
     */
    private boolean isPossible(){
        var list = dependencies.stream().filter(dependency -> repDon.stream().map(ReponseDonneeEntity::getReponsePos)
                .map(ReponsePossibleEntity::getIdReponsePos).toList()
                .contains(dependency.getIdReponsePos())).toList();
        return list.size() == dependencies.size();
    }

    /**
     * link the RepPoss with the value for this response
     * @return a map with un idRepPos for key and a value of the response given for this id
     */
    private Map<Long,Double> join(){
        var map = new HashMap<Long, Double>();
        dependencies.forEach(rP ->{
            var ga = repDon.stream().filter(reponseDonneeEntity -> reponseDonneeEntity.getReponsePos().equals(rP)).findFirst();
            if (ga.isEmpty())
                throw new IllegalStateException();
            map.put(rP.getIdReponsePos(),(rP.getConstante().getConstante() * ga.get().getEntry()));
        });
        return map;
    }

    /**
     * insert the calculation in the stack in the order of polish calculator inverse
     * @param val map create with join()
     */
    private void polishCalculator(Map<Long, Double> val){
        var iterator =  calculus.iterator();
        var calculus = iterator.next();
        if(val.size() == 1){
            var operande = new Operand(val.get(calculus.getReponsePossible().getIdReponsePos()));
            stack.push(operande);
        }
        while (iterator.hasNext()){
            Operator operator;
            switch (calculus.getCalculOp().getOperateur()){
                case ADD -> operator = new Add();
                case SUB -> operator = new Sub();
                case DIV -> operator = new Div();
                case MULT -> operator = new Mult();
                default -> operator = null;
            }
            var operand = new Operand(val.get(calculus.getReponsePossible().getIdReponsePos()));
            calculus = iterator.next();
            var operand2 = new Operand(val.get(calculus.getReponsePossible().getIdReponsePos()));

            if (stack.isEmpty()){

                stack.push(operand);
                stack.push(operand2);
                stack.push(operator);
            }
            else {
                var operator2 = stack.pop();
                if (operator != null) {
                    if (operator.level() > operator2.level()) {
                        stack.push(operand2);
                        stack.push(operator);
                        stack.push(operator2);
                    } else {
                        stack.push(operator2);
                        stack.push(operand2);
                        stack.push(operator);
                    }
                }
            }
        }
    }

    /**
     * gives the phase to which the question belongs
     * @return the phase
     */
    public Phase getPhase() {
        return phase;
    }

    public String getEntitled() {
        return entitled;
    }
}
