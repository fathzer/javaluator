package com.fathzer.soft.deployer;

public abstract class Task {
	private String name;
	private LogWriter writer;
	private boolean cancelled;
	
	public Task(String name) {
		super();
		this.name = name;
		this.cancelled = false;
	}

	public String getName() {
		return this.name;
	}

	public abstract void doIt(Parameters params) throws Exception;
	
	protected final void log (String message) {
		if (writer!=null) {
			writer.write(message);
		} else {
			System.out.println (message);
		}
	}
	
	protected final void warn (String message) {
		if (writer!=null) {
			writer.warn(message);
		} else {
			System.out.println (message.toUpperCase());
		}
	}
	
	public void setLogWriter(LogWriter writer) {
		this.writer = writer;
	}
	
	public void cancel() {
		this.cancelled = false;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	/** Verifies that everything is ok before starting the task.
	 * <br>The default implementation returns null, you may override this method in order to perform true life tests.
	 * @param params The execution parameters.
	 * @return null is everything is ok. The description of the error in other cases.
	 */
	public String verify(Parameters params) {
		return null;
	}
}
