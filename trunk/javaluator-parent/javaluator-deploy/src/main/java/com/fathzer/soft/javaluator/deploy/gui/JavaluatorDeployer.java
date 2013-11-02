package com.fathzer.soft.javaluator.deploy.gui;

import java.io.File;
import java.util.Arrays;

import com.fathzer.soft.javaluator.deploy.AppletTask;
import com.fathzer.soft.javaluator.deploy.JavadocTask;
import com.fathzer.soft.javaluator.deploy.ReleaseTask;
import com.fathzer.soft.javaluator.deploy.TutorialTask;
import com.fathzer.soft.jdeployer.DeployerGUI;
import com.fathzer.soft.jdeployer.Process;
import com.fathzer.soft.jdeployer.Task;

public class JavaluatorDeployer {
	public static void main(String[] args) {
		Process p = new Process("Deploy Javaluator", "javaluator", Arrays.asList(new Task[] {
				new ReleaseTask(), new JavadocTask(), new TutorialTask(), new AppletTask()
		}), new File("deployment"));
		new DeployerGUI(p).launch();
	}
}
