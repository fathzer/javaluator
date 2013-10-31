package com.fathzer.soft.deployer;

public abstract class Task {
	public interface LogWriter {
		public void write(String string);
	}
	
	private String name;
	private LogWriter writer;
	
	public Task(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public abstract TaskResult doIt(Parameters params);
	
	protected final void log (String message) {
		if (writer!=null) {
			writer.write(message);
		} else {
			System.out.println (message);
		}
	}
	
	public void setLogWriter(LogWriter writer) {
		this.writer = writer;
	}
}
