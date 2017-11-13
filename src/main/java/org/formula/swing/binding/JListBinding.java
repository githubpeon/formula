package org.formula.swing.binding;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;

public class JListBinding extends FormFieldBinding<JList> implements ListSelectionListener {

	public JListBinding(JList jList, FormBinder formBinder, PropertyMap propertyMap, String property, String optionsProperty, boolean required) {
		super(jList, formBinder, propertyMap, property, optionsProperty, required);
		jList.addListSelectionListener(this);
	}

	@Override
	protected void doRead() {
		getView().setSelectedValue(getPropertyValue(), true);
	}

	@Override
	protected void doReadOptions() {
        Object optionsPropertyValue = getOptionsPropertyValue();
        if(optionsPropertyValue != null) {
            if(optionsPropertyValue.getClass().isArray()) {
                getView().setListData((Object[])optionsPropertyValue);
            } else if(optionsPropertyValue instanceof Iterable) {
                ArrayList<Object> arrayList = new ArrayList<Object>();
                for(Object object : (Iterable)optionsPropertyValue) {
                    arrayList.add(object);
                }
                getView().setListData(arrayList.toArray());
            }
        }
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			write(getView().getSelectedValue());
		}
	}

}
