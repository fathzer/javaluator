package com.fathzer.soft.javaluator.deploy.gui;

import com.fathzer.soft.deployer.gui.Deployer;
import com.fathzer.soft.javaluator.deploy.JavaluatorScenario;

public class JavaluatorDeployer {
	public static void main(String[] args) {
		new Deployer(JavaluatorScenario.INSTANCE).launch();
	}
}
