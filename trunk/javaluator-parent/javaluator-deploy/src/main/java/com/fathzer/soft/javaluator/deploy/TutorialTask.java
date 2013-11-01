package com.fathzer.soft.javaluator.deploy;

import java.io.File;
import java.util.HashSet;

import com.fathzer.soft.deployer.Parameters;
import com.fathzer.soft.deployer.Task;

public class TutorialTask extends Task {

	public TutorialTask() {
		super("Tutorial");
	}

	@Override
	public String verify(Parameters params) {
		File dir = getTutoSourcesFile();
		if (!dir.exists() || !dir.isDirectory()) return "Unable to find directory "+dir.getAbsolutePath();
		return super.verify(params);
	}

	@Override
	public void doIt(Parameters params) throws Exception {
		JavaluatorScenario sc = JavaluatorScenario.INSTANCE;
		log ("Copying tutorial source examples");
		// Copy sources
		HashSet<String> copied = new HashSet<String>();
		File[] localSources = getTutoSourcesFile().listFiles();
		for (File localSource : localSources) {
			String name = localSource.getName();
			sc.copyToWeb(new File(getTutoSourcesFile(),name), "/en/doc/tutorial");
			copied.add(name);
		}
		// Delete sources files than were not just copied
		log ("delete old source examples");
		sc.deleteWebDirContent("/en/doc/tutorial", ".java", copied);
	}

	private File getTutoSourcesFile() {
		return new File(JavaluatorScenario.INSTANCE.getDeploymentDir(),"tuto");
	}
}
