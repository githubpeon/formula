package com.github.githubpeon.formula.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.githubpeon.formula.converter.DefaultConverter;
import com.github.githubpeon.formula.validation.DefaultFieldValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormField {

	String property();

	boolean required() default false;

	Class validator() default DefaultFieldValidator.class;

	Class converter() default DefaultConverter.class;

}
