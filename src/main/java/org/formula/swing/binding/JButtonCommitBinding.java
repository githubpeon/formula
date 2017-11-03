package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.formula.binding.FormBinder;
import org.formula.binding.FormBinding;
import org.formula.event.FormCommitValidationEvent;
import org.formula.event.FormCommittedEvent;
import org.formula.event.FormEditValidationEvent;
import org.formula.event.FormFieldFocusGainedEvent;
import org.formula.event.FormFieldFocusLostEvent;
import org.formula.event.FormFieldListener;
import org.formula.event.FormInitializedEvent;
import org.formula.event.FormListener;
import org.formula.event.FormPropertyEditedEvent;
import org.formula.event.FormRefreshedEvent;
import org.formula.event.FormRolledBackEvent;
import org.formula.event.FormValidationListener;
import org.formula.validation.ValidationResult;

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
