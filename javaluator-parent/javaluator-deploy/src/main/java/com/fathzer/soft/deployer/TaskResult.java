package com.fathzer.soft.deployer;

public class TaskResult {
	private String message;
	private Throwable exception;

	public TaskResult() {
		this(null,null);
	}

	public TaskResult(Throwable e, String message) {
		super();
		this.message = message;
	}

	public TaskResult(Throwable e) {
		this(e, null);
	}

	public String getMessage() {
		return message;
	}
	public Throwable getException() {
		return exception;
	}
}
