package com.fathzer.soft.deployer;

public interface LogWriter {
	public void write(String message);
	public void warn(String message);
}