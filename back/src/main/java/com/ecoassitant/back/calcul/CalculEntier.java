package com.ecoassitant.back.calcul;

import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import com.ecoassitant.back.entity.ReponseDonneeKey;
import com.ecoassitant.back.entity.ReponsePossibleEntity;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CalculEntier {
    private final List<ReponsePossibleEntity> dependances;
    private final List<CalculEntity> calculs;
    private final List<ReponseDonneeEntity> repDon;
    private final Stack<OperationElem> stack = new Stack<>();

    public CalculEntier(List<CalculEntity> calculs, List<ReponseDonneeEntity> repDon){
        this.calculs = calculs;
        this.repDon = repDon;
        dependances = new ArrayList<>();
        calculs.forEach(calculEntity -> dependances.add(calculEntity.getId().getReponsePossible()));


    }

    public Optional<Double> execute(){
        if(!isPossible())
            return Optional.empty();
        poloniser(join());

        var stack2 = new Stack<Double>();
        while (!stack.isEmpty()){
            var op = stack.pop();
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
    private boolean isPossible(){
        AtomicBoolean possible = new AtomicBoolean(true);
       dependances.forEach(dependance ->{
           if(!repDon.stream()
                   .map(ReponseDonneeEntity::getReponseDonneeKey)
                   .map(ReponseDonneeKey::getReponsePos).toList().contains(dependance)){
               possible.set(false);
           }

       });
       return possible.get();
    }
    private Map<ReponsePossibleEntity,Integer> join(){
        var map = new HashMap<ReponsePossibleEntity, Integer>();
        dependances.forEach(rP ->{
            var rD = repDon.stream().filter(reponseDonneeEntity -> reponseDonneeEntity.getReponseDonneeKey().getReponsePos().equals(rP)).findFirst();
            if (!rD.isPresent())
                throw new IllegalStateException();
            map.put(rP,(rP.getConstante().getConstante() * rD.get().getEntry()));
        });
        return map;
    }

    private void poloniser(Map<ReponsePossibleEntity, Integer> val){
        var iterateur =  calculs.iterator();
        var calcul = iterateur.next();
        while (iterateur.hasNext()){
            Operateur operateur;
            switch (calcul.getId().getCalculOp().getOperateur()){
                case ADD -> operateur = new Add();
                case SUB -> operateur = new Sub();
                case DIV -> operateur = new Div();
                case MULT -> operateur = new Mult();
                default -> operateur = null;
            }


            calcul = iterateur.next();
            var operande2 = new Operande(val.get(calcul.getId().getReponsePossible()).doubleValue());

            if (stack.isEmpty()){
                var operande = new Operande(val.get(calcul.getId().getReponsePossible()).doubleValue());
                stack.push(operande);
                stack.push(operande2);
                stack.push(operateur);
            }
            else {
                var operateur2 = stack.pop();

                if (operateur.level() > operateur2.level()){
                    stack.push(operande2);
                    stack.push(operateur);
                    stack.push(operateur2);
                }
                else{
                    stack.push(operateur2);
                    stack.push(operande2);
                    stack.push(operateur);
                }
            }
        }
    }
}
