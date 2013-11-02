package com.fathzer.soft.javaluator.deploy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import com.fathzer.soft.jdeployer.Parameters;
import com.fathzer.soft.jdeployer.Task;

public class AppletTask extends Task {

	public AppletTask() {
		super("Applet");
	}

	@Override
	public String verify(Parameters params) {
		File jar = getAppletJar(params);
		if (!jar.exists() || !jar.isFile()) return "Unable to find file "+jar.getAbsolutePath();
		return super.verify(params);
	}

	@Override
	public void doIt(Parameters params) throws Exception {
		log ("Copying demo files ...");
		String id = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		// Copy the jar file
		File jarFile = getAppletJar(params);
		String remoteName = "JavaluatorDemo"+id+".jar";
		params.getProcess().copyToWeb(jarFile, "site/demo", remoteName);
		// Copy the demoId file
		File idFile = new File("demoId.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(idFile));
		try {
			out.write(id);
		} finally {
			out.close();
		}
		params.getProcess().copyToWeb(idFile, "site/demo");
		idFile.delete();

		if (isCancelled()) return;

		// Erase the old jar files
		log ("Erasing obsolete demo files ...");
		params.getProcess().deleteWebDirContent("site/demo", ".jar", Collections.singleton(remoteName));
	}

	private File getAppletJar(Parameters params) {
		return new File(params.getProcess().getLocalRoot(),"javaluator-demo-"+params.getVersion()+".jar");
	}
}
