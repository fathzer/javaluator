package com.fathzer.soft.javaluator.deploy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import com.fathzer.soft.jdeployer.Context;
import com.fathzer.soft.jdeployer.Task;

public class AppletTask extends Task {

	public AppletTask() {
		super("Applet");
	}

	@Override
	public String verify(Context context) {
		File jar = getAppletJar(context);
		if (!jar.exists() || !jar.isFile()) {
			return "Unable to find file "+jar.getAbsolutePath();
		}
		return super.verify(context);
	}

	@Override
	public void doIt(Context context) throws Exception {
		context.log ("Copying demo files ...");
		String id = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		// Copy the jar file
		File jarFile = getAppletJar(context);
		String remoteName = "JavaluatorDemo"+id+".jar";
		context.copyToWeb(jarFile, "site/demo", remoteName);
		// Copy the demoId file
		File idFile = new File("demoId.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(idFile));
		try {
			out.write(id);
		} finally {
			out.close();
		}
		context.copyToWeb(idFile, "site/demo");
		idFile.delete();

		if (context.isCancelled()) return;

		// Erase the old jar files
		context.log ("Erasing obsolete demo files ...");
		context.deleteWebDirContent("site/demo", ".jar", Collections.singleton(remoteName));
	}

	private File getAppletJar(Context context) {
		return new File(context.getProcess().getLocalRoot(),"javaluator-demo-"+System.getProperty("appletVersion",context.getVersion())+".jar");
	}
}
