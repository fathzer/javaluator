package net.astesana.javaluator.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.astesana.javaluator.Operator;

@SuppressWarnings("serial")
public class OperatorTableModel extends AbstractTableModel {
	private List<Operator<? extends Object>> operators;
	
	public OperatorTableModel(Collection<Operator<? extends Object>> collection) {
		this.operators = new ArrayList<Operator<? extends Object>>(collection);
		Collections.sort(this.operators, new Comparator<Operator<? extends Object>>() {
			public int compare(Operator<? extends Object> o1, Operator<? extends Object> o2) {
				int result = o1.getPrecedence()-o2.getPrecedence();
				return result;
			}
		});
	}

	public int getColumnCount() {
		return 4;
	}

	public int getRowCount() {
		return operators.size();
	}

	public Object getValueAt(int row, int column) {
		Operator<? extends Object> ope = operators.get(row);
		if (column==0) return ope.getSymbol(); 
		if (column==1) return getDescription(ope); 
		if (column==2) return ope.getPrecedence();
		if (column==3) return ope.getAssociativity();
		if (column==4) return ope.getOperandCount();
		return null;
	}

	private String getDescription(Operator<? extends Object> ope) {
		String key = ope.getSymbol()+ope.getOperandCount();
		return Messages.getString(key);
	}

	@Override
	public String getColumnName(int column) {
		if (column==0) return "Symbol"; //$NON-NLS-1$
		if (column==1) return "Description";		 //$NON-NLS-1$
		if (column==2) return "Precedence"; //$NON-NLS-1$
		if (column==3) return "Associativity"; //$NON-NLS-1$
		if (column==4) return "Arity"; //$NON-NLS-1$
		return super.getColumnName(column);
	}
}
