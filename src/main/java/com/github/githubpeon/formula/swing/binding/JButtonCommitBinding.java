package com.github.githubpeon.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormBinding;
import com.github.githubpeon.formula.event.FormCommitValidationEvent;
import com.github.githubpeon.formula.event.FormCommittedEvent;
import com.github.githubpeon.formula.event.FormEditValidationEvent;
import com.github.githubpeon.formula.event.FormFieldFocusGainedEvent;
import com.github.githubpeon.formula.event.FormFieldFocusLostEvent;
import com.github.githubpeon.formula.event.FormFieldListener;
import com.github.githubpeon.formula.event.FormInitializedEvent;
import com.github.githubpeon.formula.event.FormListener;
import com.github.githubpeon.formula.event.FormPropertyEditedEvent;
import com.github.githubpeon.formula.event.FormRefreshedEvent;
import com.github.githubpeon.formula.event.FormRolledBackEvent;
import com.github.githubpeon.formula.event.FormValidationListener;
import com.github.githubpeon.formula.validation.ValidationResult;

public class JButtonCommitBinding extends FormBinding<JButton> implements ActionListener, FormListener, FormFieldListener, FormValidationListener {

	public JButtonCommitBinding(JButton jButton, FormBinder formBinder) {
		super(jButton, formBinder);
		jButton.setEnabled(false);
		jButton.addActionListener(this);
		formBinder.addFormListener(this);
		formBinder.addFormFieldListener(this);
		formBinder.addFormValidationListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getFormBinder().commit();
	}

	@Override
	public void formPropertyEdited(FormPropertyEditedEvent e) {
		// We don't care about this.
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

	@Override
	public void formFieldFocusGained(FormFieldFocusGainedEvent e) {
		SwingUtilities.getRootPane(getView()).setDefaultButton(getView());
	}

	@Override
	public void formFieldFocusLost(FormFieldFocusLostEvent e) {
		// We don't care about this.
	}

	@Override
	public void formEditValidation(FormEditValidationEvent e) {
		ValidationResult validationResult = e.getValidationResult();
		if (getView().isEnabled()
				&& validationResult.isMissingRequiredProperties()) {
			getView().setEnabled(false);
		} else if (!getView().isEnabled()
				&& !validationResult.isMissingRequiredProperties()) {
			getView().setEnabled(true);
		}
	}

	@Override
	public void formCommitValidation(FormCommitValidationEvent e) {
		// We don't care about this
	}

}
