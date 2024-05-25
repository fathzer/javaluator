package com.fathzer.soft.javaluator.demo;

import javax.swing.JFrame;

public class DemoApplet {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Javaluator demo");
		frame.setContentPane(new DemoPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
