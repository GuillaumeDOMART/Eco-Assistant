package com.ecoassitant.back.calcul;

import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import com.ecoassitant.back.entity.ReponseDonneeKey;
import com.ecoassitant.back.entity.ReponsePossibleEntity;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * calcul create with part of calcul(calculEntity) and ReponseDonne for a project
 */
public class CalculEntier {
    private final List<ReponsePossibleEntity> dependances;
    private final List<CalculEntity> calculs;
    private final List<ReponseDonneeEntity> repDon;
    private final Stack<OperationElem> stack = new Stack<>();

    /**
     * Constructor of CalculEntier
     * @param calculs list of calculs recovery of the data
     * @param repDon list of reponseDonnee for a project
     */
    public CalculEntier(List<CalculEntity> calculs, List<ReponseDonneeEntity> repDon){
        this.calculs = List.copyOf(calculs);
        this.repDon = List.copyOf(repDon);
        dependances = new ArrayList<>();
        calculs.forEach(calculEntity -> dependances.add(calculEntity.getReponsePossible()));
    }

    /**
     * Try to execute the calcul if it has all dependances
     * @return the result of the calcul if possible
     */
    public Optional<Double> execute(){
        if(!isPossible())
            return Optional.empty();
        poloniser(join());
        System.out.println("stack= " + stack);
        var stack2 = new Stack<Double>();
        var stack3 = new Stack<OperationElem>();
        while (!stack.isEmpty())
            stack3.push(stack.pop());
        System.out.println(stack3);
        while (!stack3.isEmpty()){
            System.out.println(stack2);
            var op = stack3.pop();
            if(op.type().equals(TypeOp.OPERANDE)) {
                Operande operande = (Operande) op;
                stack2.push(operande.val());
            }
            else {
                var y = stack2.pop();
                var x = stack2.pop();
                Operateur operateur = (Operateur) op;
                stack2.push(operateur.execute(x,y));
            }
        }
        return Optional.of(stack2.pop());
    }

    /**
     * check if the calcul have all dependances
     * @return true if all depandances has a response
     */
    private boolean isPossible(){
        AtomicBoolean possible = new AtomicBoolean(true);
        dependances.forEach(dependance ->{
            var str = repDon.stream().map(ReponseDonneeEntity::getReponseDonneeKey)
                    .map(ReponseDonneeKey::getReponsePos)
                    .map(ReponsePossibleEntity::getIdReponsePos).toList();
            if(!str.contains(dependance.getIdReponsePos())){
                possible.set(false);
            }
        });
       return possible.get();
    }

    /**
     * link the RepPoss with the value for this response
     * @return a map with un idRepPos for key and a value of the response donne for this id
     */
    private Map<Long,Integer> join(){
        var map = new HashMap<Long, Integer>();
        dependances.forEach(rP ->{
            var rD = repDon.stream().filter(reponseDonneeEntity -> reponseDonneeEntity.getReponseDonneeKey().getReponsePos().equals(rP)).findFirst();
            if (rD.isEmpty())
                throw new IllegalStateException();
            map.put(rP.getIdReponsePos(),(rP.getConstante().getConstante() * rD.get().getEntry()));
        });
        return map;
    }

    /**
     * insert the calcul in the stack in the order of polish calculator inverse
     * @param val map create with join()
     */
    private void poloniser(Map<Long, Integer> val){
        var iterateur =  calculs.iterator();
        var calcul = iterateur.next();
        while (iterateur.hasNext()){
            Operateur operateur;
            switch (calcul.getCalculOp().getOperateur()){
                case ADD -> operateur = new Add();
                case SUB -> operateur = new Sub();
                case DIV -> operateur = new Div();
                case MULT -> operateur = new Mult();
                default -> operateur = null;
            }
            var operande = new Operande(val.get(calcul.getReponsePossible().getIdReponsePos()).doubleValue());
            calcul = iterateur.next();
            var operande2 = new Operande(val.get(calcul.getReponsePossible().getIdReponsePos()).doubleValue());

            if (stack.isEmpty()){

                stack.push(operande);
                stack.push(operande2);
                stack.push(operateur);
            }
            else {
                if(operateur != null) {
                    var operateur2 = stack.pop();
                    if (operateur.level() > operateur2.level()) {
                        stack.push(operande2);
                        stack.push(operateur);
                        stack.push(operateur2);
                    } else {
                        stack.push(operateur2);
                        stack.push(operande2);
                        stack.push(operateur);
                    }
                }
            }
            System.out.println(stack);
        }
    }
}
