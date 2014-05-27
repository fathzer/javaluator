package com.fathzer.soft.javaluator.deploy;

import java.io.File;

import com.fathzer.soft.jdeployer.Context;
import com.fathzer.soft.jdeployer.Task;

public class ReleaseTask extends Task {

	public ReleaseTask() {
		super("Release");
	}

	@Override
	public String verify(Context context) {
		File file = getReleaseFile(context);
		if (!file.exists() || !file.isFile()) return "Unable to find file "+file.getAbsolutePath();
		return super.verify(context);
	}

	@Override
	public void doIt(Context context) throws Exception {
		context.log ("Uploading release to SourceForge");
		context.copyToRelease(getReleaseFile(context), ".");
	}

	private File getReleaseFile(Context context) {
		return new File(context.getProcess().getLocalRoot(),"Javaluator-v"+context.getVersion()+".zip");
	}
}
