package com.github.githubpeon.formula.event;

public interface FormListener {

	public void formPropertyEdited(FormPropertyEditedEvent e);

	public void formCommitted(FormCommittedEvent e);

}
