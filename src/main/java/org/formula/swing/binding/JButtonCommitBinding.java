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
import org.formula.event.FormEnableEvent;
import org.formula.event.FormEnableListener;
import org.formula.event.FormFieldFocusGainedEvent;
import org.formula.event.FormFieldFocusLostEvent;
import org.formula.event.FormFieldListener;
import org.formula.event.FormInitializedEvent;
import org.formula.event.FormListener;
import org.formula.event.FormPropertyEditedEvent;
import org.formula.event.FormRefreshedEvent;
import org.formula.event.FormRolledBackEvent;
import org.formula.event.FormValidationListener;
import org.formula.validation.ValidationError;
import org.formula.validation.ValidationMessage;
import org.formula.validation.ValidationResult;

public class JButtonCommitBinding extends FormBinding<JButton> implements ActionListener, FormListener, FormFieldListener, FormValidationListener, FormEnableListener {

	private String defaultToolTipText;

	public JButtonCommitBinding(JButton jButton, FormBinder formBinder) {
		super(jButton, formBinder);
		jButton.setEnabled(false);
		jButton.addActionListener(this);
		formBinder.addFormListener(this);
		formBinder.addFormFieldListener(this);
		formBinder.addFormValidationListener(this);
		formBinder.addFormEnableListener(this);
		this.defaultToolTipText = jButton.getToolTipText();
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
		if (validationResult.hasErrors()
				|| !validationResult.isAllowCommit()) {
			getView().setEnabled(false);
			updateToolTipText(validationResult);
		} else if (validationResult.isMissingRequiredProperties()) {
			getView().setEnabled(false);
			updateToolTipText(null);
		} else {
			getView().setEnabled(true);
			updateToolTipText(null);
		}
	}

	private void updateToolTipText(ValidationResult validationResult) {
		if (validationResult == null) {
			getView().setToolTipText(this.defaultToolTipText);
		} else {
			getView().setEnabled(false);
			StringBuilder stringBuilder = new StringBuilder();
			for (ValidationMessage validationMessage : validationResult.getPropertyValidationMessages()) {
				if (!validationMessage.getMessage().equals(ValidationError.REQUIRED_FIELD_MISSING)
						&& !stringBuilder.toString().contains(validationMessage.getMessage().toString())) {
					stringBuilder.append(validationMessage.getMessage() + "\n");
				}
			}
			for (ValidationMessage validationMessage : validationResult.getFormValidationMessages()) {
				stringBuilder.append(validationMessage.getMessage() + "\n");
			}
			if (!stringBuilder.toString().isEmpty()) {
				getView().setToolTipText("<html>" + stringBuilder.toString().replace("\n", "<br>") + "</html>");
			} else {
				getView().setToolTipText(this.defaultToolTipText);
			}
		}
	}

	@Override
	public void formCommitValidation(FormCommitValidationEvent e) {
		// We don't care about this
	}

	@Override
	public void formEnable(FormEnableEvent e) {
		getView().setEnabled(e.isEnable());
		if (e.isRequestFocus()) {
			getView().requestFocus();
		}
	}

}
