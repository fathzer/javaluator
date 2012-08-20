package examples;

import net.astesana.javaluator.DoubleEvaluator;

public class SimplestWay {
public static void main(String[] args) {
DoubleEvaluator evaluator = new DoubleEvaluator(); String expression = "(2^3-1)*sin(pi/4)/ln(pi^2)";
Double result = evaluator.evaluate(expression);
System.out.println (expression+" = "+result);
}
}