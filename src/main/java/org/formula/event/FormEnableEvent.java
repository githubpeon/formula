package org.formula.event;

import org.formula.binding.FormBinder;

public class FormEnableEvent extends FormEvent {

	private boolean enable = false;
	private boolean requestFocus = false;
	private static final long serialVersionUID = -5250005313959967893L;

	public FormEnableEvent(FormBinder source, boolean enable, boolean requestFocus) {
		super(source);
		this.enable = enable;
		this.requestFocus = requestFocus;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isRequestFocus() {
		return requestFocus;
	}

	public void setRequestFocus(boolean requestFocus) {
		this.requestFocus = requestFocus;
	}

}
