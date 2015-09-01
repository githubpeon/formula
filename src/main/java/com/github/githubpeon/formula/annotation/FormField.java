package com.github.githubpeon.formula.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.githubpeon.formula.validation.DefaultFieldValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormField {

	String value();

	boolean required() default false;

	Class validator() default DefaultFieldValidator.class;

}
