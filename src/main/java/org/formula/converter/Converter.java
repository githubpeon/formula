package org.formula.converter;

public interface Converter<T extends Object, U extends Object> {

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
