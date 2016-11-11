package com.fathzer.soft.javaluator.junit;

import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import net.jodah.concurrentunit.ConcurrentTestCase;
import net.jodah.concurrentunit.Waiter;

public class MultithreadTest extends ConcurrentTestCase {
	private static final DoubleEvaluator EVALUATOR = new DoubleEvaluator();
	
	@Test
	public void test() throws TimeoutException {
		final Waiter waiter = new Waiter();

		for (int i = 0; i < 100; i++) {
			final int x = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					String expression = "sin("+x+"+(3/2))";
					double result = EVALUATOR.evaluate(expression);
					waiter.assertTrue(Math.abs(Math.sin(x+1.5)-result)<0.0000001);
					waiter.resume();
				}

			}).start();
		}

		// Wait for resume() to be called
		waiter.await(1000);
	}
}
