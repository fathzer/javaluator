package net.astesana.javaluator.demo;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import net.astesana.ajlib.swing.Utils;
import net.astesana.ajlib.swing.widget.TextWidget;
import net.astesana.javaluator.AbstractEvaluator;
import net.astesana.javaluator.Constant;
import net.astesana.javaluator.DoubleEvaluator;
import net.astesana.javaluator.Function;
import net.astesana.javaluator.Operator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class DemoPanel extends JPanel {
	private JLabel lblNewLabel;
	private TextWidget expression;
	private JLabel lblNewLabel_1;
	private JLabel resultLabel;
	
	private AbstractEvaluator<? extends Object> evaluator;
	private JPanel panel;
	private JPanel operatorsPanel;
	private JScrollPane scrollPane;
	private JTable operatorsTable;
	private JPanel panel_1;
	private JPanel constantsPanel;
	private JScrollPane scrollPane_1;
	private JTable constantsTable;
	private JPanel functionsPanel;
	private JScrollPane scrollPane_2;
	private JTable functionsTable;

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
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridwidth = 2;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(getPanel(), gbc_panel);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.weighty = 1.0;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		add(getPanel_1(), gbc_panel_1);
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
//			getOperatorsTable().setFillsViewportHeight(true);
		}
		return scrollPane;
	}
	private JTable getOperatorsTable() {
		if (operatorsTable == null) {
			Collection<?> operators = getEvaluator().getOperators();
			operatorsTable = new JTable(new OperatorTableModel((Collection<Operator<? extends Object>>) operators));
			Utils.packColumns(operatorsTable, 2);
		}
		return operatorsTable;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			panel_1.setLayout(gbl_panel_1);
			GridBagConstraints gbc_operatorsPanel = new GridBagConstraints();
			gbc_operatorsPanel.insets = new Insets(0, 0, 5, 0);
			gbc_operatorsPanel.weighty = 1.0;
			gbc_operatorsPanel.weightx = 1.0;
			gbc_operatorsPanel.fill = GridBagConstraints.BOTH;
			gbc_operatorsPanel.gridx = 0;
			gbc_operatorsPanel.gridy = 0;
			panel_1.add(getOperatorsPanel(), gbc_operatorsPanel);
			GridBagConstraints gbc_constantsPanel = new GridBagConstraints();
			gbc_constantsPanel.insets = new Insets(0, 0, 5, 0);
			gbc_constantsPanel.weighty = 0.3;
			gbc_constantsPanel.weightx = 1.0;
			gbc_constantsPanel.fill = GridBagConstraints.BOTH;
			gbc_constantsPanel.gridx = 0;
			gbc_constantsPanel.gridy = 1;
			panel_1.add(getConstantsPanel(), gbc_constantsPanel);
			GridBagConstraints gbc_functionsPanel = new GridBagConstraints();
			gbc_functionsPanel.weighty = 1.0;
			gbc_functionsPanel.weightx = 1.0;
			gbc_functionsPanel.fill = GridBagConstraints.BOTH;
			gbc_functionsPanel.gridheight = 0;
			gbc_functionsPanel.gridx = 1;
			gbc_functionsPanel.gridy = 0;
			panel_1.add(getFunctionsPanel(), gbc_functionsPanel);
		}
		return panel_1;
	}
	private JPanel getConstantsPanel() {
		if (constantsPanel == null) {
			constantsPanel = new JPanel();
			constantsPanel.setBorder(new TitledBorder(null, "Constants", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			constantsPanel.setLayout(new BorderLayout(0, 0));
			constantsPanel.add(getScrollPane_1());
		}
		return constantsPanel;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getConstantsTable());
		}
		return scrollPane_1;
	}
	private JTable getConstantsTable() {
		if (constantsTable == null) {
			Collection<?> constants = getEvaluator().getConstants();
			constantsTable = new JTable(new ConstantTableModel((Collection<Constant<? extends Object>>) constants));
			Utils.packColumns(constantsTable, 2);
		}
		return constantsTable;
	}
	private JPanel getFunctionsPanel() {
		if (functionsPanel == null) {
			functionsPanel = new JPanel();
			functionsPanel.setBorder(new TitledBorder(null, "Functions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			functionsPanel.setLayout(new BorderLayout(0, 0));
			functionsPanel.add(getScrollPane_2());
		}
		return functionsPanel;
	}
	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setViewportView(getFunctionsTable());
		}
		return scrollPane_2;
	}
	private JTable getFunctionsTable() {
		if (functionsTable == null) {
			Collection<?> functions = getEvaluator().getFunctions();
			functionsTable = new JTable(new FunctionTableModel((Collection<Function<? extends Object>>) functions));
			Utils.packColumns(functionsTable, 2);
		}
		return functionsTable;
	}
}
