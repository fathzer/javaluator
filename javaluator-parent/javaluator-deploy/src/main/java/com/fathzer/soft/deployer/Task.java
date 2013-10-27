package com.fathzer.soft.deployer;

public abstract class Task {
	private String name;
	
	public Task(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public abstract TaskResult doIt(Parameters params);
}
