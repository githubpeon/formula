package org.formula.swing.binding;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;

import org.formula.binding.FormBinder;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;
import org.formula.swing.table.FormulaTableModel;

/**
 * TODO: This binding is read only for now. That needs to change eventually.
 */
public class JTableBinding extends SwingFormFieldBinding<JTable> implements ListSelectionListener, RowSorterListener {

	private String selectionProperty;
	private String filteredProperty;

	public JTableBinding(JTable jTable, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, boolean errorIndicator, Converter converter, String selectionProperty, String filteredProperty) {
		super(jTable, formBinder, propertyMap, property, labelProperties, optionsProperty, required, errorIndicator, converter);

		if (!selectionProperty.isEmpty()) {
			getPropertyMap().put(selectionProperty, null);
			jTable.getSelectionModel().addListSelectionListener(this);
			this.selectionProperty = selectionProperty;
		}
		
		if (!filteredProperty.isEmpty()) {
			getPropertyMap().put(filteredProperty, null);
			jTable.getRowSorter().addRowSorterListener(this);
			this.filteredProperty = filteredProperty;
		}		
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doRead() {
		if (getPropertyValue() != null) {
			if (getView().getModel() instanceof FormulaTableModel) {
				FormulaTableModel formulaTableModel = (FormulaTableModel) getView().getModel();
				formulaTableModel.setObjects((List) getPropertyValue());
			} else {
				getView().setModel((TableModel) getPropertyValue());
			}
		}
	}

	@Override
	protected void doReadLabel() {

	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	public void enable(boolean enable) {
		getView().setEnabled(enable);
	}

	@Override
	public void focus() {
		getView().requestFocusInWindow();
	}

	@SuppressWarnings("unchecked")
	protected void doWriteSelection() {
		if (getView().getModel() instanceof FormulaTableModel) {
			FormulaTableModel formulaTableModel = (FormulaTableModel) getView().getModel();
			List selectedObjects = new ArrayList();

			int[] selectedRows = getView().getSelectedRows();
			for (int row : selectedRows) {
				selectedObjects.add(formulaTableModel.getObjects().get(row));
			}

			if (getView().getSelectionModel().getSelectionMode() == ListSelectionModel.SINGLE_SELECTION) {
				if (selectedObjects.size() > 0) {
					getPropertyMap().put(this.selectionProperty, selectedObjects.get(0));
				} else {
					getPropertyMap().put(this.selectionProperty, null);
				}
			} else {
				getPropertyMap().put(this.selectionProperty, selectedObjects);
			}
			getFormBinder().commitProperty(this.selectionProperty);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting() && this.selectionProperty != null) {
			doWriteSelection();
		}
	}

	@SuppressWarnings("unchecked")
	protected void doWriteFilter() {
		if (getView().getModel() instanceof FormulaTableModel) {
			FormulaTableModel formulaTableModel = (FormulaTableModel) getView().getModel();
			List filteredObjects = new ArrayList();

            for(int row = 0;row < getView().getRowCount(); ++row) {
            	filteredObjects.add(formulaTableModel.getObjects().get(getView().convertRowIndexToModel(row)));
            }
            
			getPropertyMap().put(this.filteredProperty, filteredObjects);

			getFormBinder().commitProperty(this.filteredProperty);
		}
	}	
	
	@Override
	public void sorterChanged(RowSorterEvent e) {
		doWriteFilter();
	}
}
