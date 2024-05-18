package com.fathzer.soft.javaluator.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Operator.Associativity;

public class OperatorTest {

	@Test
	public void test() {
		final Operator plus = new Operator("+",2,Associativity.LEFT, 1);
		assertEquals(DoubleEvaluator.PLUS, plus);
		assertNotEquals(plus, new Operator("-",2,Associativity.LEFT, 1));
		assertNotEquals(plus, new Operator("+",1,Associativity.LEFT, 1));
		assertNotEquals(plus, new Operator("+",2,Associativity.RIGHT, 1));
		assertNotEquals(plus, new Operator("+",2,Associativity.LEFT, 2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptySymbol() {
		new Operator("",2,Associativity.LEFT, 1);
	}
	@Test(expected = IllegalArgumentException.class)
	public void trailingBlankSymbol() {
		new Operator("+ ",2,Associativity.LEFT, 1);
	}
	@Test(expected = IllegalArgumentException.class)
	public void noSymbol() {
		new Operator(null,2,Associativity.LEFT, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noAssociativity() {
		new Operator("+",2,null, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void OperandCountTooHigh() {
		new Operator("+",3,Associativity.LEFT, 1);
	}
	@Test(expected = IllegalArgumentException.class)
	public void OperandCountTooLow() {
		new Operator("+",0,Associativity.LEFT, 1);
	}
}
