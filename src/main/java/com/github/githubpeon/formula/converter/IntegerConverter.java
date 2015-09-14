package com.github.githubpeon.formula.converter;

public class IntegerConverter implements Converter<Integer, String> {

	@Override
	public String convertFrom(Integer value) {
		return String.valueOf(value);
	}

	@Override
	public Integer convertTo(String value) {
		return (value == null || value.isEmpty() ? 0 : Integer.decode(value));
	}

}
