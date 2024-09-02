package com.fathzer.soft.javaluator.demo;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTable;

import com.fathzer.soft.ajlib.swing.Utils;
import com.fathzer.soft.ajlib.swing.widget.TextWidget;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.DoubleEvaluator;

@SuppressWarnings("serial")
public class DemoPanel extends JPanel {
	private JLabel lblNewLabel;
	private TextWidget expression;
	private JLabel lblNewLabel1;
	private JLabel resultLabel;
	
	private AbstractEvaluator<? extends Object> evaluator;
	private JPanel panel;
	private JPanel operatorsPanel;
	private JScrollPane scrollPane;
	private JTable operatorsTable;
	private JPanel panel1;
	private JPanel constantsPanel;
	private JScrollPane scrollPane1;
	private JTable constantsTable;
	private JPanel functionsPanel;
	private JScrollPane scrollPane2;
	private JTable functionsTable;

	/**
	 * Create the panel.
	 */
	public DemoPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
		gbcLblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbcLblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbcLblNewLabel.gridx = 0;
		gbcLblNewLabel.gridy = 0;
		add(getLblNewLabel(), gbcLblNewLabel);
		GridBagConstraints gbcExpression = new GridBagConstraints();
		gbcExpression.insets = new Insets(0, 0, 5, 0);
		gbcExpression.fill = GridBagConstraints.HORIZONTAL;
		gbcExpression.weightx = 1.0;
		gbcExpression.gridx = 1;
		gbcExpression.gridy = 0;
		add(getExpression(), gbcExpression);
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.insets = new Insets(0, 0, 5, 0);
		gbcPanel.fill = GridBagConstraints.BOTH;
		gbcPanel.gridwidth = 2;
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;
		add(getPanel(), gbcPanel);
		GridBagConstraints gbcPanel1 = new GridBagConstraints();
		gbcPanel1.weighty = 1.0;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.gridwidth = 2;
		gbcPanel1.gridx = 0;
		gbcPanel1.gridy = 2;
		add(getPanel1(), gbcPanel1);
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
			expression.addPropertyChangeListener(TextWidget.TEXT_PROPERTY, evt -> {
				try {
					String exp = expression.getText();
					Object result = exp.length()==0 ? exp : getEvaluator().evaluate(exp);
					getResultLabel().setText(result.toString());
					resultLabel.setIcon(null);
					resultLabel.setForeground(Color.BLACK);
				} catch (IllegalArgumentException e) {
					getResultLabel().setText("error: "+e);
					resultLabel.setForeground(Color.RED);
				}
			});
			expression.setColumns(30);
		}
		return expression;
	}
	private JLabel getLblNewLabel1() {
		if (lblNewLabel1 == null) {
			lblNewLabel1 = new JLabel("Javaluator's reply = ");
		}
		return lblNewLabel1;
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
			GridBagLayout gblPanel = new GridBagLayout();
			panel.setLayout(gblPanel);
			GridBagConstraints gbcLblNewLabel1 = new GridBagConstraints();
			gbcLblNewLabel1.anchor = GridBagConstraints.EAST;
			gbcLblNewLabel1.insets = new Insets(0, 0, 0, 5);
			gbcLblNewLabel1.gridx = 0;
			gbcLblNewLabel1.gridy = 0;
			panel.add(getLblNewLabel1(), gbcLblNewLabel1);
			GridBagConstraints gbcResultLabel = new GridBagConstraints();
			gbcResultLabel.weightx = 1.0;
			gbcResultLabel.fill = GridBagConstraints.HORIZONTAL;
			gbcResultLabel.gridx = 1;
			gbcResultLabel.gridy = 0;
			panel.add(getResultLabel(), gbcResultLabel);
		}
		return panel;
	}
	private JPanel getOperatorsPanel() {
		if (operatorsPanel == null) {
			operatorsPanel = new JPanel();
			operatorsPanel.setBorder(new TitledBorder(null, "Operators", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			operatorsPanel.setLayout(new BorderLayout(0, 0));
			operatorsPanel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return operatorsPanel;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getOperatorsTable());
		}
		return scrollPane;
	}
	private JTable getOperatorsTable() {
		if (operatorsTable == null) {
			operatorsTable = new JTable(new OperatorTableModel(getEvaluator().getOperators()));
			Utils.packColumns(operatorsTable, 2);
		}
		return operatorsTable;
	}
	private JPanel getPanel1() {
		if (panel1 == null) {
			panel1 = new JPanel();
			GridBagLayout gblPanel1 = new GridBagLayout();
			panel1.setLayout(gblPanel1);
			GridBagConstraints gbcOperatorsPanel = new GridBagConstraints();
			gbcOperatorsPanel.insets = new Insets(0, 0, 5, 0);
			gbcOperatorsPanel.weighty = 1.0;
			gbcOperatorsPanel.weightx = 1.0;
			gbcOperatorsPanel.fill = GridBagConstraints.BOTH;
			gbcOperatorsPanel.gridx = 0;
			gbcOperatorsPanel.gridy = 0;
			panel1.add(getOperatorsPanel(), gbcOperatorsPanel);
			GridBagConstraints gbcConstantsPanel = new GridBagConstraints();
			gbcConstantsPanel.insets = new Insets(0, 0, 5, 0);
			gbcConstantsPanel.weighty = 0.3;
			gbcConstantsPanel.weightx = 1.0;
			gbcConstantsPanel.fill = GridBagConstraints.BOTH;
			gbcConstantsPanel.gridx = 0;
			gbcConstantsPanel.gridy = 1;
			panel1.add(getConstantsPanel(), gbcConstantsPanel);
			GridBagConstraints gbcFunctionsPanel = new GridBagConstraints();
			gbcFunctionsPanel.weighty = 1.0;
			gbcFunctionsPanel.weightx = 1.0;
			gbcFunctionsPanel.fill = GridBagConstraints.BOTH;
			gbcFunctionsPanel.gridheight = 0;
			gbcFunctionsPanel.gridx = 1;
			gbcFunctionsPanel.gridy = 0;
			panel1.add(getFunctionsPanel(), gbcFunctionsPanel);
		}
		return panel1;
	}
	private JPanel getConstantsPanel() {
		if (constantsPanel == null) {
			constantsPanel = new JPanel();
			constantsPanel.setBorder(new TitledBorder(null, "Constants", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			constantsPanel.setLayout(new BorderLayout(0, 0));
			constantsPanel.add(getScrollPane1());
		}
		return constantsPanel;
	}
	private JScrollPane getScrollPane1() {
		if (scrollPane1 == null) {
			scrollPane1 = new JScrollPane();
			scrollPane1.setViewportView(getConstantsTable());
		}
		return scrollPane1;
	}
	private JTable getConstantsTable() {
		if (constantsTable == null) {
			constantsTable = new JTable(new ConstantTableModel(getEvaluator().getConstants()));
			Utils.packColumns(constantsTable, 2);
		}
		return constantsTable;
	}
	private JPanel getFunctionsPanel() {
		if (functionsPanel == null) {
			functionsPanel = new JPanel();
			functionsPanel.setBorder(new TitledBorder(null, "Functions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			functionsPanel.setLayout(new BorderLayout(0, 0));
			functionsPanel.add(getScrollPane2());
		}
		return functionsPanel;
	}
	private JScrollPane getScrollPane2() {
		if (scrollPane2 == null) {
			scrollPane2 = new JScrollPane();
			scrollPane2.setViewportView(getFunctionsTable());
		}
		return scrollPane2;
	}
	private JTable getFunctionsTable() {
		if (functionsTable == null) {
			functionsTable = new JTable(new FunctionTableModel(getEvaluator().getFunctions()));
			Utils.packColumns(functionsTable, 2);
		}
		return functionsTable;
	}
}
