package org.formula.event;

public interface FormListener {

	public void formPropertyEdited(FormPropertyEditedEvent e);

	public void formInitialized(FormInitializedEvent e);

	public void formPropertyRefreshed(FormPropertyRefreshedEvent e);

	public void formRefreshed(FormRefreshedEvent e);

	public void formCommitted(FormCommittedEvent e);

	public void formRolledBack(FormRolledBackEvent e);

}
