package com.fathzer.soft.javaluator.deploy;

import com.fathzer.soft.deployer.Parameters;
import com.fathzer.soft.deployer.Task;
import com.fathzer.soft.deployer.TaskResult;

public class TutorialTask extends Task {

	public TutorialTask() {
		super("Tutorial");
	}

	@Override
	public TaskResult doIt(Parameters param) {
		try {
			log("starting");
			Thread.sleep(1000);
			log("second step");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		// TODO Auto-generated method stub
		return null;
	}

}
