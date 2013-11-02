package com.fathzer.soft.javaluator.deploy;

import java.io.File;

import com.fathzer.soft.jdeployer.Parameters;
import com.fathzer.soft.jdeployer.Task;

public class JavadocTask extends Task {

	public JavadocTask() {
		super("Javadoc");
	}

	@Override
	public String verify(Parameters params) {
		File file = getRelNotesFile(params);
		if (!file.exists() || !file.isFile()) return "Unable to find file "+file.getAbsolutePath();
		file = getJavaDocDirectory(params);
		if (!file.exists() || !file.isDirectory()) return "Unable to find directory "+file.getAbsolutePath();
		return super.verify(params);
	}

	@Override
	public void doIt(Parameters params) throws Exception {
		log ("Copying releaseNotes");
		// Relnotes
		params.getProcess().copyToWeb(getRelNotesFile(params), "/en/doc");

		// Copy javadoc
		log ("Copying javadoc");
		params.getProcess().copyToWeb(getJavaDocDirectory(params), "/en/doc", "javadoc");
	}
	
	private File getRelNotesFile(Parameters params) {
		return new File(params.getProcess().getLocalRoot(), "relnotes.txt");
	}
	private File getJavaDocDirectory(Parameters params) {
		return new File(params.getProcess().getLocalRoot(), "doc");
	}
}
