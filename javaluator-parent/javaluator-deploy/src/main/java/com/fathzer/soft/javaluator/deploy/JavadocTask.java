package com.fathzer.soft.javaluator.deploy;

import java.io.File;

import com.fathzer.soft.deployer.Parameters;
import com.fathzer.soft.deployer.Task;

public class JavadocTask extends Task {

	public JavadocTask() {
		super("Javadoc");
	}

	@Override
	public String verify(Parameters params) {
		File file = getRelNotesFile();
		if (!file.exists() || !file.isFile()) return "Unable to find file "+file.getAbsolutePath();
		file = getJavaDocDirectory();
		if (!file.exists() || !file.isDirectory()) return "Unable to find directory "+file.getAbsolutePath();
		return super.verify(params);
	}

	@Override
	public void doIt(Parameters params) throws Exception {
		log ("Copying releaseNotes");
		// Relnotes
		JavaluatorScenario.INSTANCE.copyToWeb(getRelNotesFile(), "/en/doc");

		// Copy javadoc
		log ("Copying javadoc");
		JavaluatorScenario.INSTANCE.copyToWeb(getJavaDocDirectory(), "/en/doc", "javadoc");
	}
	
	private File getRelNotesFile() {
		return new File(JavaluatorScenario.INSTANCE.getDeploymentDir(), "relnotes.txt");
	}
	private File getJavaDocDirectory() {
		return new File(JavaluatorScenario.INSTANCE.getDeploymentDir(), "doc");
	}
}
