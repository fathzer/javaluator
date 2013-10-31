package com.fathzer.soft.javaluator.deploy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSelector;

import com.fathzer.soft.deployer.Parameters;
import com.fathzer.soft.deployer.Task;

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
	public void doIt(Parameters param) throws Exception {
		//FIXME Take the demo in the deployment directory
		JavaluatorScenario sc = JavaluatorScenario.INSTANCE;
		log ("Getting previous demo files ...");
		final FileObject fDemo = sc.getFsManager().resolveFile(sc.getWebRoot()+"/site/demo", sc.getOpts());
		// Get previous jar files
		FileObject[] foundFiles = fDemo.findFiles(new FileSelector() {
			@Override
			public boolean traverseDescendents(FileSelectInfo info) throws Exception {
				return info.getFile().equals(fDemo);
			}
			
			@Override
			public boolean includeFile(FileSelectInfo info) throws Exception {
				return info.getFile().getName().getBaseName().endsWith(".jar");
			}
		});
		if (foundFiles==null) foundFiles = new FileObject[0];
		
		if (isCancelled()) return;
		
		log ("Copying demo files ...");
		String id = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		// Copy the jar file
		File jarFile = getAppletJar(param);
		String remoteName = "JavaluatorDemo"+id+".jar";
		sc.copyToWeb(jarFile, "site/demo", remoteName);
		// Copy the demoId file
		File idFile = new File("demoId.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(idFile));
		try {
			out.write(id);
		} finally {
			out.close();
		}
		sc.copyToWeb(idFile, "site/demo");
		idFile.delete();

		if (isCancelled()) return;

		// Erase the old jar files
		log ("Erasing obsolete demo files ...");
		for (FileObject fileObject : foundFiles) {
			String baseName = fileObject.getName().getBaseName();
			if (!baseName.equals(remoteName)) {
				if (!fileObject.delete()) {
					warn ("ALERT : unable to delete file "+fileObject);
				}
			}
		}
	}

	private File getAppletJar(Parameters param) {
		return new File(JavaluatorScenario.INSTANCE.getDeploymentDir()+"javaluator-demo-"+param.getVersion()+".jar");
	}
}
