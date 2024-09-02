package com.fathzer.soft.javaluator.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.fathzer.soft.javaluator.Function;

@SuppressWarnings("serial")
public class FunctionTableModel extends AbstractTableModel {
	private List<Function> functions;
	
	public FunctionTableModel(Collection<Function> collection) {
		this.functions = new ArrayList<>(collection);
		Collections.sort(this.functions, new Comparator<>() {
			public int compare(Function f1, Function f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});
	}

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return functions.size();
	}

	public Object getValueAt(int row, int column) {
		Function function = functions.get(row);
		if (column==0) return function.getName(); 
		if (column==1) return getDescription(function); 
		if (column==2) {
			int min = function.getMinimumArgumentCount();
			int max = function.getMaximumArgumentCount();
			if (min==max) return min;
			if (max==Integer.MAX_VALUE) return "> "+min;
			return "> "+min+" and < "+max;
		}
		return null;
	}

	private String getDescription(Function function) {
		return Messages.getString(function.getName());
	}

	@Override
	public String getColumnName(int column) {
		if (column==0) return "Name";
		if (column==1) return "Description";		
		if (column==2) return "Nb args";
		return super.getColumnName(column);
	}
}
