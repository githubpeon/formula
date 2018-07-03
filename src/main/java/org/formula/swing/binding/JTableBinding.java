package org.formula.swing.binding;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.formula.binding.FormBinder;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;
import org.formula.swing.table.FormulaTableModel;

/**
 * TODO: This binding is read only for now. That needs to change eventually.
 */
public class JTableBinding extends SwingFormFieldBinding<JTable> implements ListSelectionListener {

	private String selectionProperty;

	public JTableBinding(JTable jTable, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, String selectionProperty, boolean required, boolean errorIndicator, Converter converter) {
		super(jTable, formBinder, propertyMap, property, labelProperties, optionsProperty, required, errorIndicator, converter);
		jTable.getSelectionModel().addListSelectionListener(this);

		this.selectionProperty = selectionProperty;
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
			FormulaTableModel formulaTabelModel = (FormulaTableModel) getView().getModel();
			List selectedObjects = new ArrayList();

			int[] selectedRows = getView().getSelectedRows();
			for (int row : selectedRows) {
				selectedObjects.add(formulaTabelModel.getObjects().get(row));
			}

			getPropertyMap().put(this.selectionProperty, selectedObjects);
			getFormBinder().commitProperty(this.selectionProperty);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			doWriteSelection();
		}
	}

}
