package com.fathzer.soft.javaluator.deploy;

import java.util.Arrays;
import java.util.List;

import com.fathzer.soft.deployer.Scenario;
import com.fathzer.soft.deployer.Task;

public class JavaluatorScenario extends Scenario {
	public static final Scenario INSTANCE = new JavaluatorScenario();
	private static final String RELEASE_ROOT = "sftp://web.sourceforge.net/home/pfs/project/javaluator";
	private static final String WEB_ROOT = "sftp://web.sourceforge.net/home/project-web/javaluator/htdocs";
	
	private List<Task> tasks;
	
	private JavaluatorScenario() {
		super("Deploy Javaluator", RELEASE_ROOT, WEB_ROOT);
		tasks = Arrays.asList(new Task[] {new ReleaseTask(), new JavadocTask(), new TutorialTask(), new AppletTask()});
	}

	@Override
	public List<Task> getTasks() {
		return tasks;
	}
}
