package Solver;


import Data.*;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MonteCarlo {
    double epsilon = 0.00001;
    double accuracy = 1000000;
    EnterData data;
    double[] bestVariablesValues;
    double[] randomPoint;
    double bestResult;
    double radius = 100;
    double previousResult;
    Operators operators = new Operators();
    Map<String,Double> values = new HashMap<>();
    Expression expressionObjectiveFunction;
    Expression[] expressionConstrains;
    int divideNumber = 2;

    public MonteCarlo(EnterData data) {
        this.data = data;
        randomPoint =new double[data.numberOfVariables];
        if(data.objective.equals("max")){
            bestResult=Double.NEGATIVE_INFINITY;
        }
        else if(data.objective.equals("min")){
            bestResult=Double.POSITIVE_INFINITY;
        }
        bestVariablesValues = new double[data.numberOfVariables];
        previousResult=0;
    }
    public void Solve(){
        MakeObjectiveAndConstrainsExpressions();
        for(int i=0;i<accuracy;i++){
            Seed();
            Optimize();
        }
        FindBest();
    }

    void MakeObjectiveAndConstrainsExpressions(){
        expressionObjectiveFunction = new ExpressionBuilder(data.objectiveFunction).variables(data.variables)
                .build();
        expressionConstrains = new Expression[data.numberOfConstrains];
        for(int i=0;i<data.numberOfConstrains;i++){
            expressionConstrains[i] = new ExpressionBuilder(data.constrains[i]).variables(data.variables)
                    .operator(operators.lessEqual,operators.moreEqual,operators.equal).build();
        }

    }
    void FindBest(){
       if(CheckIfEnd()){
           return;
       }
        previousResult=bestResult;
        for(int i=0;i<accuracy;i++){
            Search(radius);
            Optimize();
        }
        radius/=divideNumber;
        FindBest();

    }
    void Optimize(){
        expressionObjectiveFunction.setVariables(values);
        if(data.objective.equals("max")){
           if((bestResult=Math.max(bestResult,expressionObjectiveFunction.evaluate()))==expressionObjectiveFunction.evaluate()){
               bestVariablesValues=randomPoint.clone();
           }
        }else{
           if((bestResult=Math.min(bestResult,expressionObjectiveFunction.evaluate()))==expressionObjectiveFunction.evaluate()){
                bestVariablesValues=randomPoint.clone();
           }
        }
    }
    void Seed(){
        double lowerBound;
        double upperBound;
        for (int i=0;i<data.numberOfVariables;i++){
            lowerBound= data.boundaries[i][0];
            upperBound = data.boundaries[i][1];
            randomPoint[i]=ThreadLocalRandom.current().nextDouble(lowerBound,upperBound);
            values.put(data.variables[i],randomPoint[i]);
        }
        if(!CheckIfPossible()){
            Seed();
        }
    }

    void Search(double radius){
       double upperBound;
       double lowerBound;
       for(int i=0;i<data.numberOfVariables;i++){
           lowerBound=Math.max(data.boundaries[i][0],bestVariablesValues[i]-radius);
           upperBound=Math.min(data.boundaries[i][1],bestVariablesValues[i]+radius);
           randomPoint[i]=ThreadLocalRandom.current().nextDouble(lowerBound,upperBound);
           values.put(data.variables[i],randomPoint[i]);
       }
       if(!CheckIfPossible()){
          Seed();
       }
    }

    boolean CheckIfPossible(){
        for(int i=0;i<data.numberOfConstrains;i++){
            expressionConstrains[i].setVariables(values);
            if(expressionConstrains[i].evaluate()==0d){
                return false;
            }
        }
        return true;
    }

    boolean CheckIfEnd(){
        if((Math.abs(bestResult-previousResult))<epsilon){
            System.out.println(Arrays.toString(bestVariablesValues));
            System.out.println(bestResult);
            return true;
        }
        return false;
    }
}
