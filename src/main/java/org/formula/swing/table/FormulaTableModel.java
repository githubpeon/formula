package org.formula.swing.table;

import java.util.List;

public interface FormulaTableModel<T extends Object> {

	public void setObjects(List<T> objects);

	public List<T> getObjects();

}
