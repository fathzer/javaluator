package net.astesana.javaluator.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.astesana.javaluator.Operator;

public class OperatorTableModel extends AbstractTableModel {
	private List<Operator<? extends Object>> operators;
	
	public OperatorTableModel(Collection<Operator<? extends Object>> collection) {
		this.operators = new ArrayList<Operator<? extends Object>>(collection);
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return operators.size();
	}

	public Object getValueAt(int row, int column) {
		Operator<? extends Object> ope = operators.get(row);
		if (column==0) return ope.getSymbol(); 
		if (column==1) return ope.getPrecedence(); 
		return null;
	}

	@Override
	public String getColumnName(int column) {
		if (column==0) return "Symbol"; 
		if (column==1) return "Precedence";
		return super.getColumnName(column);
	}
}
