package com.fathzer.soft.javaluator.demo;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class DemoApplet extends JApplet {
	// Called when this applet is loaded into the browser.
	public void init() {
		// Execute a job on the event-dispatching thread; creating this applet's GUI.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					DemoPanel lbl = new DemoPanel();
					add(lbl);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("createGUI didn't complete successfully");
		}
	}
}
