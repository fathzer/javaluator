package com.fathzer.soft.javaluator.deploy;

import java.io.File;

import com.fathzer.soft.jdeployer.Parameters;
import com.fathzer.soft.jdeployer.Task;

public class ReleaseTask extends Task {

	public ReleaseTask() {
		super("Release");
	}

	@Override
	public String verify(Parameters params) {
		File file = getReleaseFile(params);
		if (!file.exists() || !file.isFile()) return "Unable to find file "+file.getAbsolutePath();
		return super.verify(params);
	}

	@Override
	public void doIt(Parameters params) throws Exception {
		log ("Uploading release to SourceForge");
		params.getProcess().copyToRelease(getReleaseFile(params), ".");
	}

	private File getReleaseFile(Parameters params) {
		return new File(params.getProcess().getLocalRoot(),"Javaluator-v"+params.getVersion()+".zip");
	}
}
