package org.formula.swing.binding;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.formula.binding.FormBinder;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

/**
 * TODO: This binding is only putting the table model into the view at the moment and if the table is edited,
 * the table model is edited directly. It still needs change detection, rollback/commit support etc.
 */
public class JTableBinding extends SwingFormFieldBinding<JTable> {

	public JTableBinding(JTable jTable, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, boolean errorIndicator, Converter converter) {
		super(jTable, formBinder, propertyMap, property, labelProperties, optionsProperty, required, errorIndicator, converter);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setModel((TableModel) getPropertyValue());
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

}
