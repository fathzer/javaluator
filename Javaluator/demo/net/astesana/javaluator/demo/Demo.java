package net.astesana.javaluator.demo;

import java.awt.Container;

import net.astesana.ajlib.swing.framework.Application;

public class Demo extends Application{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Demo().launch();
	}

	@Override
	protected Container buildMainPanel() {
		return new DemoPanel();
	}

	@Override
	protected boolean onStart() {
		getJFrame().pack();
		return true;
	}

	/* (non-Javadoc)
	 * @see net.astesana.ajlib.swing.framework.Application#getName()
	 */
	@Override
	public String getName() {
		return "Javaluator demo";
	}

	/* (non-Javadoc)
	 * @see net.astesana.ajlib.swing.framework.Application#saveState()
	 */
	@Override
	protected void saveState() {
		// Do nothing ... as the jar will not be signed we have no rights to save preferences;
	}

	/* (non-Javadoc)
	 * @see net.astesana.ajlib.swing.framework.Application#restoreState()
	 */
	@Override
	protected void restoreState() {
		// Do nothing ... as the jar will not be signed we have no rights to restore preferences;
	}

}
