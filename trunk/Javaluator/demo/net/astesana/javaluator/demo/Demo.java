package net.astesana.javaluator.demo;

import java.awt.Container;
import java.io.ObjectInputStream.GetField;

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

}
