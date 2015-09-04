package com.github.githubpeon.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormBinding;
import com.github.githubpeon.formula.event.FormCommittedEvent;
import com.github.githubpeon.formula.event.FormListener;
import com.github.githubpeon.formula.event.FormPropertyEditedEvent;
import com.github.githubpeon.formula.event.FormRolledBackEvent;

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
	public void formCommitted(FormCommittedEvent e) {
		getView().setEnabled(false);
	}

	@Override
	public void formRolledBack(FormRolledBackEvent e) {
		getView().setEnabled(false);
	}

}
