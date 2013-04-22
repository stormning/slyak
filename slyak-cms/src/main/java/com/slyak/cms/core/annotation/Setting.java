package com.slyak.cms.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.slyak.cms.core.enums.InputType;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Setting {
	String key();
	
	String value() default "";
	//TODO
	String[] options() default {};
	
	InputType inputType() default InputType.INPUT;
}