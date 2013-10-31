package com.fathzer.soft.deployer.gui;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class TextPaneWriter {
	private Style style;
	private StyledDocument document;

	public TextPaneWriter(JTextPane textPane) {
		this.document = textPane.getStyledDocument();
		StyleContext context = new StyleContext();
		this.style = context.addStyle("test", null);
	}
	
	public void append(String message, Color color) {
		StyleConstants.setForeground(style, color);	
		try {
			document.insertString(document.getLength(), message, style);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

}
