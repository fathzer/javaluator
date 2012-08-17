package net.astesana.javaluator.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.astesana.javaluator.Constant;

public class ConstantTableModel extends AbstractTableModel {
	private List<Constant<? extends Object>> constants;
	
	public ConstantTableModel(Collection<Constant<? extends Object>> collection) {
		this.constants = new ArrayList<Constant<? extends Object>>(collection);
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return constants.size();
	}

	public Object getValueAt(int row, int column) {
		Constant<? extends Object> constant = constants.get(row);
		if (column==0) return constant.getMnemonic(); 
		if (column==1) return getDescription(row); 
		return null;
	}

	private String getDescription(int row) {
		return null; //TODO
	}

	@Override
	public String getColumnName(int column) {
		if (column==0) return "Mnemonic";
		if (column==1) return "Description";		
		return super.getColumnName(column);
	}
}
