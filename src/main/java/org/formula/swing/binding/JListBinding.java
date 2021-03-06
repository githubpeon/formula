package org.formula.swing.binding;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.formula.binding.FormBinder;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JListBinding extends SwingFormFieldBinding<JList> implements ListSelectionListener {

	public JListBinding(JList jList, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, boolean errorIndicator, Converter converter) {
		super(jList, formBinder, propertyMap, property, labelProperties, optionsProperty, required, errorIndicator, converter);
		jList.addListSelectionListener(this);
	}

	@Override
	protected void doRead() {
		getView().setSelectedValue(getPropertyValue(), true);
	}

	@Override
	protected void doReadLabel() {

	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doReadOptions() {
		Object optionsPropertyValue = getOptionsPropertyValue();
		if (optionsPropertyValue != null) {
			if (optionsPropertyValue.getClass().isArray()) {
				getView().setListData((Object[]) optionsPropertyValue);
			} else if (optionsPropertyValue instanceof Iterable) {
				ArrayList<Object> arrayList = new ArrayList<Object>();
				for (Object object : (Iterable) optionsPropertyValue) {
					arrayList.add(object);
				}
				getView().setListData(arrayList.toArray());
			}
		}
		doRead();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			write(getView().getSelectedValue());
		}
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
