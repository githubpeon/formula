package com.github.githubpeon.formula.converter;

/**
 * @param <T> The class of the property in the model.
 * @param <U> The class of the property in the view.
 */
public interface Converter<T, U> {

	/**
	 * Converts FROM the model TO the view.
	 * 
	 * @param value
	 * @return
	 */
	public U convertFrom(T value);

	/**
	 * Converts TO the model FROM the view.
	 * 
	 * @param value
	 * @return
	 */
	public T convertTo(U value);

}
