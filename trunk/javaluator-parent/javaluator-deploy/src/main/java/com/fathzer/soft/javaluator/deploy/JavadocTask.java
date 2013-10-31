package com.fathzer.soft.javaluator.deploy;

import com.fathzer.soft.deployer.Parameters;
import com.fathzer.soft.deployer.Task;
import com.fathzer.soft.deployer.TaskResult;

public class JavadocTask extends Task {

	public JavadocTask() {
		super("Javadoc");
	}

	@Override
	public TaskResult doIt(Parameters param) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// TODO Auto-generated method stub
		return null;
	}

}
