package com.github.githubpeon.formula.event;

import java.util.EventObject;

import com.github.githubpeon.formula.binding.FormBinder;

public class FormEvent extends EventObject {

	public enum FormEventId {
		FORM_EDITED,
		FORM_FIELD_FOCUS_GAINED,
		FORM_FIELD_FOCUS_LOST,
		FORM_COMMITTED;
	}

	private static final long serialVersionUID = 3940726011165626441L;
	private final FormEventId formEventId;
	private final Object formField;

	public FormEvent(FormBinder source, FormEventId formEventId) {
		this(source, formEventId, null);
	}

	public FormEvent(FormBinder source, FormEventId formEventId, Object formField) {
		super(source);
		this.formEventId = formEventId;
		this.formField = formField;
	}

	public FormEventId getFormEventId() {
		return formEventId;
	}

	public Object getFormField() {
		return formField;
	}

}
