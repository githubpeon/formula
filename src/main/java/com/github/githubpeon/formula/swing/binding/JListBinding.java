package com.github.githubpeon.formula.swing.binding;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;

public class JListBinding extends FormFieldBinding<JList> implements ListSelectionListener {

	public JListBinding(JList jList, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jList, formBinder, propertyMap, property, required);
		jList.addListSelectionListener(this);
	}

	@Override
	protected void doRead() {
		getView().setSelectedValue(getPropertyValue(), true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			write(getView().getSelectedValue());
		}
	}

}
