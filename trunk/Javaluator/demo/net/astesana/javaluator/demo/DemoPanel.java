package net.astesana.javaluator.demo;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import net.astesana.ajlib.swing.widget.TextWidget;
import net.astesana.javaluator.AbstractEvaluator;
import net.astesana.javaluator.DoubleEvaluator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Insets;

@SuppressWarnings("serial")
public class DemoPanel extends JPanel {
	private JLabel lblNewLabel;
	private TextWidget expression;
	private JLabel lblNewLabel_1;
	private JLabel resultLabel;
	
	private AbstractEvaluator<? extends Object> evaluator;
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public DemoPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(getLblNewLabel(), gbc_lblNewLabel);
		GridBagConstraints gbc_expression = new GridBagConstraints();
		gbc_expression.insets = new Insets(0, 0, 5, 0);
		gbc_expression.fill = GridBagConstraints.HORIZONTAL;
		gbc_expression.weightx = 1.0;
		gbc_expression.gridx = 1;
		gbc_expression.gridy = 0;
		add(getExpression(), gbc_expression);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(getPanel(), gbc_panel);
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Type your expression here:");
		}
		return lblNewLabel;
	}
	private TextWidget getExpression() {
		if (expression == null) {
			expression = new TextWidget();
			expression.addPropertyChangeListener(TextWidget.TEXT_PROPERTY, new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					try {
						Object result = getEvaluator().evaluate(expression.getText());
						getResultLabel().setText(result.toString());
						resultLabel.setIcon(null);
					} catch (IllegalArgumentException e) {
						getResultLabel().setText("error: "+e);
						resultLabel.setIcon(null); //TODO
					}
				}
			});
			expression.setColumns(30);
		}
		return expression;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Javaluator's reply = ");
		}
		return lblNewLabel_1;
	}
	private JLabel getResultLabel() {
		if (resultLabel == null) {
			resultLabel = new JLabel(" ");
		}
		return resultLabel;
	}

	private AbstractEvaluator<? extends Object> getEvaluator() {
		if (evaluator==null) {
			evaluator = new DoubleEvaluator();
		}
		return evaluator;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 0;
			panel.add(getLblNewLabel_1(), gbc_lblNewLabel_1);
			GridBagConstraints gbc_resultLabel = new GridBagConstraints();
			gbc_resultLabel.weightx = 1.0;
			gbc_resultLabel.fill = GridBagConstraints.HORIZONTAL;
			gbc_resultLabel.gridx = 1;
			gbc_resultLabel.gridy = 0;
			panel.add(getResultLabel(), gbc_resultLabel);
		}
		return panel;
	}
}
