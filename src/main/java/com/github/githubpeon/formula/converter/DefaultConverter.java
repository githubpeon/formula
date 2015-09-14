package com.github.githubpeon.formula.converter;

public class DefaultConverter implements Converter<Object, Object> {

	@Override
	public Object convertFrom(Object value) {
		return value;
	}

	@Override
	public Object convertTo(Object value) {
		return value;
	}

}
