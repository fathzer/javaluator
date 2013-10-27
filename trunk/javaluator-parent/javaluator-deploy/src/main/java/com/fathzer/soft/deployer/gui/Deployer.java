package com.fathzer.soft.deployer.gui;

import java.awt.Container;

import com.fathzer.soft.ajlib.swing.framework.Application;
import com.fathzer.soft.deployer.Scenario;

public class Deployer extends Application {
	private DeployPanel deployPanel;
	private Scenario scenario;
	
	public Deployer(Scenario scenario) {
		super();
		this.scenario = scenario;
	}

	public Scenario getScenario() {
		return scenario;
	}

	@Override
	protected Container buildMainPanel() {
		deployPanel = new DeployPanel(getScenario());
		return deployPanel;
	}
	
	@Override
	public String getName() {
		return getScenario().getName();
	}

	@Override
	protected void saveState() {
		super.saveState();
		deployPanel.saveState();
	}

	@Override
	protected void restoreState() {
		super.restoreState();
		deployPanel.restoreState();
	}
}
