package net.astesana.javaluator.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.astesana.javaluator.Constant;

@SuppressWarnings("serial")
public class ConstantTableModel extends AbstractTableModel {
	private List<Constant> constants;
	
	public ConstantTableModel(Collection<Constant> collection) {
		this.constants = new ArrayList<Constant>(collection);
		Collections.sort(this.constants, new Comparator<Constant>() {
			public int compare(Constant c1, Constant c2) {
				return c1.getMnemonic().compareTo(c2.getMnemonic());
			}
		});
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return constants.size();
	}

	public Object getValueAt(int row, int column) {
		Constant constant = constants.get(row);
		if (column==0) return constant.getMnemonic(); 
		if (column==1) return getDescription(constant); 
		return null;
	}

	private String getDescription(Constant constant) {
		return Messages.getString(constant.getMnemonic());
	}

	@Override
	public String getColumnName(int column) {
		if (column==0) return "Mnemonic";
		if (column==1) return "Description";		
		return super.getColumnName(column);
	}
}
