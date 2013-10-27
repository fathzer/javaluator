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
import com.fathzer.soft.deployer.Scenario;
import com.fathzer.soft.deployer.Task;
import com.fathzer.soft.deployer.TaskResult;

public class AppletTask extends Task {

	public AppletTask() {
		super("Applet");
	}

	@Override
	public TaskResult doIt(Parameters param) {
		System.out.println ("Copying demo");
		try {
			Scenario sc = JavaluatorScenario.INSTANCE;
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
			
			String id = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
			// Copy the jar file
			File jarFile = new File("../javaluator-demo/target/javaluator-demo-"+param.getVersion()+".jar");
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
			// Erase the old jar files
			for (FileObject fileObject : foundFiles) {
				String baseName = fileObject.getName().getBaseName();
				if (!baseName.equals(remoteName)) {
					if (!fileObject.delete()) {
						System.err.println("ALERT : unable to delete file "+fileObject);
					}
				}
			}
			return null;
		} catch (Throwable e) {
			e.printStackTrace();
			return new TaskResult(e);
		}
	}
}
