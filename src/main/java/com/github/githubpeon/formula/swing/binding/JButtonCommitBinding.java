package com.github.githubpeon.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormBinding;
import com.github.githubpeon.formula.event.FormEvent;
import com.github.githubpeon.formula.event.FormEvent.FormEventId;

public class JButtonCommitBinding extends FormBinding implements ActionListener {

	public JButtonCommitBinding(JButton jButton, FormBinder formBinder) {
		super(jButton, formBinder);
		jButton.setEnabled(false);
		jButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getFormBinder().commit();
	}

	@Override
	public void handleFormEvent(FormEvent e) {
		if (e.getFormEventId() == FormEventId.FORM_EDITED) {
			((JButton) getView()).setEnabled(true);
		} else if (e.getFormEventId() == FormEventId.FORM_COMMITTED) {
			((JButton) getView()).setEnabled(false);
		} else if (e.getFormEventId() == FormEventId.FORM_FIELD_FOCUS_GAINED) {
			SwingUtilities.getRootPane((JButton) getView()).setDefaultButton((JButton) getView());
		}
	}
}
