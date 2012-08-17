package net.astesana.javaluator.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.astesana.javaluator.Function;

public class FunctionTableModel extends AbstractTableModel {
	private List<Function<? extends Object>> functions;
	
	public FunctionTableModel(Collection<Function<? extends Object>> collection) {
		this.functions = new ArrayList<Function<? extends Object>>(collection);
	}

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return functions.size();
	}

	public Object getValueAt(int row, int column) {
		Function<? extends Object> function = functions.get(row);
		if (column==0) return function.getName(); 
		if (column==1) return getDescription(row); 
		if (column==2) {
			int min = function.getMinimumArgumentCount();
			int max = function.getMaximumArgumentCount();
			if (min==max) return min;
			if (max==Integer.MAX_VALUE) return "> "+min;
			return "> "+min+" and < "+max;
		}
		return null;
	}

	private String getDescription(int row) {
		return null; //TODO
	}

	@Override
	public String getColumnName(int column) {
		if (column==0) return "Name";
		if (column==1) return "Description";		
		if (column==2) return "Number of arguments";
		return super.getColumnName(column);
	}
}
