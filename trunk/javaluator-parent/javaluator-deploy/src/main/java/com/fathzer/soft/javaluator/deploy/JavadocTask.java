package com.fathzer.soft.javaluator.deploy;

import java.io.File;

import com.fathzer.soft.jdeployer.Context;
import com.fathzer.soft.jdeployer.Task;

public class JavadocTask extends Task {

	public JavadocTask() {
		super("Javadoc");
	}

	@Override
	public String verify(Context context) {
		File file = getRelNotesFile(context);
		if (!file.exists() || !file.isFile()) return "Unable to find file "+file.getAbsolutePath();
		file = getJavaDocDirectory(context);
		if (!file.exists() || !file.isDirectory()) return "Unable to find directory "+file.getAbsolutePath();
		return super.verify(context);
	}

	@Override
	public void doIt(Context context) throws Exception {
		context.log ("Copying releaseNotes");
		// Relnotes
		context.copyToWeb(getRelNotesFile(context), "/en/doc");

		// Copy javadoc
		context.log ("Copying javadoc");
		context.copyToWeb(getJavaDocDirectory(context), "/en/doc", "javadoc");
	}
	
	private File getRelNotesFile(Context context) {
		return new File(context.getProcess().getLocalRoot(), "relnotes.txt");
	}
	private File getJavaDocDirectory(Context context) {
		return new File(context.getProcess().getLocalRoot(), "doc");
	}
}
