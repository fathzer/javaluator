package com.fathzer.soft.javaluator.deploy;

import com.fathzer.soft.deployer.Parameters;
import com.fathzer.soft.deployer.Task;

public class JavadocTask extends Task {

	public JavadocTask() {
		super("Javadoc");
	}

	@Override
	public void doIt(Parameters param) {
		try {
			Thread.sleep(1000);
			throw new RuntimeException ("Exception test");
		} catch (InterruptedException e) {
		}
		// TODO Auto-generated method stub
	}

}
