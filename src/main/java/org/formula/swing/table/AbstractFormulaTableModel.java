package org.formula.swing.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractFormulaTableModel<T extends Object> extends AbstractTableModel implements FormulaTableModel<T> {

	private List<T> objects;
	private static final long serialVersionUID = -5926303326976741096L;

	@Override
	public List<T> getObjects() {
		return objects;
	}

	@Override
	public void setObjects(List<T> objects) {
		this.objects = objects;
		fireTableDataChanged();
	}

}
