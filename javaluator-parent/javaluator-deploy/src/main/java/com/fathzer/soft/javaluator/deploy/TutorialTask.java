package com.fathzer.soft.javaluator.deploy;

import java.io.File;
import java.util.HashSet;

import com.fathzer.soft.jdeployer.Parameters;
import com.fathzer.soft.jdeployer.Task;

public class TutorialTask extends Task {

	public TutorialTask() {
		super("Tutorial");
	}

	@Override
	public String verify(Parameters params) {
		File dir = getTutoSourcesFile(params);
		if (!dir.exists() || !dir.isDirectory()) return "Unable to find directory "+dir.getAbsolutePath();
		return super.verify(params);
	}

	@Override
	public void doIt(Parameters params) throws Exception {
		log ("Copying tutorial source examples");
		// Copy sources
		HashSet<String> copied = new HashSet<String>();
		File[] localSources = getTutoSourcesFile(params).listFiles();
		for (File localSource : localSources) {
			String name = localSource.getName();
			params.getProcess().copyToWeb(new File(getTutoSourcesFile(params),name), "/en/doc/tutorial");
			copied.add(name);
		}
		// Delete sources files than were not just copied
		log ("delete old source examples");
		params.getProcess().deleteWebDirContent("/en/doc/tutorial", ".java", copied);
	}

	private File getTutoSourcesFile(Parameters params) {
		return new File(params.getProcess().getLocalRoot(),"tuto");
	}
}
