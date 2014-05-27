package com.fathzer.soft.javaluator.deploy;

import java.io.File;
import java.util.HashSet;

import com.fathzer.soft.jdeployer.Context;
import com.fathzer.soft.jdeployer.Task;

public class TutorialTask extends Task {

	public TutorialTask() {
		super("Tutorial");
	}

	@Override
	public String verify(Context context) {
		File dir = getTutoSourcesFile(context);
		if (!dir.exists() || !dir.isDirectory()) return "Unable to find directory "+dir.getAbsolutePath();
		return super.verify(context);
	}

	@Override
	public void doIt(Context context) throws Exception {
		context.log ("Copying tutorial source examples");
		// Copy sources
		HashSet<String> copied = new HashSet<String>();
		File[] localSources = getTutoSourcesFile(context).listFiles();
		for (File localSource : localSources) {
			String name = localSource.getName();
			context.copyToWeb(new File(getTutoSourcesFile(context),name), "/en/doc/tutorial");
			copied.add(name);
		}
		// Delete sources files than were not just copied
		context.log ("delete old source examples");
		context.deleteWebDirContent("/en/doc/tutorial", ".java", copied);
	}

	private File getTutoSourcesFile(Context context) {
		return new File(context.getProcess().getLocalRoot(),"tuto");
	}
}
