package org.formula.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.formula.converter.DefaultConverter;
import org.formula.validation.DefaultFieldValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormField {

	String property() default "";

    String[] labelProperties() default {};

	String optionsProperty() default "";

	boolean required() default false;

	boolean errorIndicator() default true;

	Class validator() default DefaultFieldValidator.class;

	Class converter() default DefaultConverter.class;

}
