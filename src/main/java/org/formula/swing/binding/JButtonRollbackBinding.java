package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.formula.binding.FormBinder;
import org.formula.binding.FormBinding;
import org.formula.event.FormCommittedEvent;
import org.formula.event.FormInitializedEvent;
import org.formula.event.FormListener;
import org.formula.event.FormPropertyEditedEvent;
import org.formula.event.FormRefreshedEvent;
import org.formula.event.FormRolledBackEvent;

public class JButtonRollbackBinding extends FormBinding<JButton> implements ActionListener, FormListener {

	public JButtonRollbackBinding(JButton jButton, FormBinder formBinder) {
		super(jButton, formBinder);
		jButton.setEnabled(false);
		jButton.addActionListener(this);
		formBinder.addFormListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getFormBinder().rollback();
	}

	@Override
	public void formPropertyEdited(FormPropertyEditedEvent e) {
		getView().setEnabled(true);
	}

	@Override
	public void formInitialized(FormInitializedEvent e) {
		getView().setEnabled(false);
	}

	@Override
	public void formRefreshed(FormRefreshedEvent e) {
		getView().setEnabled(false);
	}

	@Override
	public void formCommitted(FormCommittedEvent e) {
		getView().setEnabled(false);
	}

	@Override
	public void formRolledBack(FormRolledBackEvent e) {
		getView().setEnabled(false);
	}

}
