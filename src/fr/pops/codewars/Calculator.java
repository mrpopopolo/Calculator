package fr.pops.codewars;
import java.util.*;

public class Calculator {

	public static void main(String[] args) {
		Calculator calc = new Calculator();
		calc.run();
	}
	
	public void run() {
		String expression = "(123.45*(678.90/(-2.5+11.5)-1*(((80-1*(19)))*33.25))/20)-1*(123.45*(678.90/(-2.5+11.5)-1*(((80-1*(19)))*33.25))/20)+(13-2)/-1*(-11)";
		expression = formatExpr(expression);
		System.out.println(expression + " = ");
	    System.out.println(calculate(expression));
	}
	
	  
	  public double calculate(String expression) {
	    Deque<String> stack = new LinkedList<>();
	    int i = 0, j;
	    
	    while(i < expression.length()){
	      j = i;
	      StringBuilder number = new StringBuilder();
	      
	      while(j < expression.length() && expression.substring(i, j+1).matches("^-?[0-9]*[.]?[0-9]*$")){
	        number.append(expression.charAt(j));
	        j++;
	      }
	      if(number.length() > 0){
	        stack.addLast(expression.substring(i, j));
	        i += j-i;
	        continue;
	        }
	      
	      if(expression.charAt(i) == '('){
	        j = findClosingPar(expression.substring(i));
	        
	        stack.addLast(calculate(expression.substring(i+1, i+j))+"");
	        i += j+1;
	        continue;
	        }
	    
	    stack.addLast(expression.charAt(i)+"");
	    i++;
	    }
	  
	  String numA, op, numB;
	  while(stack.contains("*") || stack.contains("/")){
	    numA = stack.pop();
	    op = stack.pop();
	    if(op.charAt(0) == '-' || (op.charAt(0) >= '0' && op.charAt(0) <= '9')){
	      numB = op;
	      op = "+";
	      }
	    else {
	      numB = stack.pop();
	      }
	    
	    switch(op){
	      case "*":
	        stack.push(""+(Double.parseDouble(numA) * Double.parseDouble(numB)));
	      break;
	      case "/":
	        stack.push(""+(Double.parseDouble(numA) / Double.parseDouble(numB)));
	      break;
	      default:
	        stack.addLast(op);
	        stack.addLast(numA);
	        stack.addFirst(numB);
	      }
	    }
	  
	  if(stack.peekLast().equals("+")) stack.addLast(stack.pop());
	  while(stack.size() > 1){
	    numA = stack.pop();
	    op = stack.pop();
	    if(op.charAt(0) == '-' || (op.charAt(0) >= '0' && op.charAt(0) <= '9')){
	      numB = op;
	      op = "+";
	      }
	    else {
	      numB = stack.pop();
	      }
	      
	    switch(op){
	      case "+":
	        stack.addFirst(""+(Double.parseDouble(numA) + Double.parseDouble(numB)));
	        if(stack.size() <= 1) break;
	      break;
	      case "-":
	        stack.addFirst(""+(Double.parseDouble(numA) - Double.parseDouble(numB)));
	        if(stack.size() <= 1) break;
	      }
	    }
	  
	  return Double.parseDouble(stack.pop());
	  }
	  
	  public String formatExpr(String expr){
	    expr = expr.replaceAll(" ", "");
	    expr = expr.replaceAll("--", "+");
	    expr = expr.replaceAll("[+]-", "-");
	    
	    while(expr.contains("-(")){
	      int beg = expr.indexOf("-(");
	      int end = findClosingPar(expr.substring(beg+1)) + beg;
	      expr = expr.substring(0, beg)+"(-1*"+expr.substring(beg+1, end+1)+")"+expr.substring(end+1);
	      }
	    
	    return expr;
	    }
	  
	  public int findClosingPar(String exp){
	    int parenth = 1, count = 0;
	    while(parenth > 0){
	      count++;
	      if(exp.charAt(count) == '(')
	        parenth++;
	      if(exp.charAt(count) == ')')
	        parenth--;
	      }
	    return count;
	    }

}
