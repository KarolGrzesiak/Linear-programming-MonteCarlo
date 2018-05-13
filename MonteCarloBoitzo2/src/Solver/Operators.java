package Solver;

import net.objecthunter.exp4j.operator.Operator;

public class Operators {
    Operator lessEqual = new Operator("<=",2,true,Operator.PRECEDENCE_ADDITION-1) {
        @Override
        public double apply(double[] values) {
            if(values[0]<=values[1]){
                return 1d;
            }
            else
                return 0d;
        }
    } ;
    Operator moreEqual = new Operator(">=",2,true,Operator.PRECEDENCE_ADDITION-1) {
        @Override
        public double apply(double[] values) {
            if(values[0]>=values[1]){
                return 1d;
            }
            else
                return 0d;
        }
    } ;
    Operator equal = new Operator("=",2,true,Operator.PRECEDENCE_ADDITION-1) {
        @Override
        public double apply(double[] values) {
            if(values[0]==values[1]){
                return 1d;
            }
            else
                return 0d;
        }
    } ;
}
