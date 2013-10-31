package com.fathzer.soft.javaluator.deploy;

import com.fathzer.soft.deployer.Parameters;
import com.fathzer.soft.deployer.Task;

public class TutorialTask extends Task {

	public TutorialTask() {
		super("Tutorial");
	}

	@Override
	public void doIt(Parameters param) {
		try {
			log("starting");
			Thread.sleep(1000);
			if (isCancelled()) return;
			log("second step");
			warn("A small problem occurred");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		// TODO Auto-generated method stub
	}

}
