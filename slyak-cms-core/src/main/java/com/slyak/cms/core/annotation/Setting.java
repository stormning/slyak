/**
 * Project name : slyak-cms-core
 * File name : Setting.java
 * Package name : com.slyak.cms.core.annotation
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
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
	
	String name();
	
	String key();
	
	String value() default "";
	
	NameAndValue[] options() default {};
	
	String optionsLoader() default "";
	
	InputType inputType() default InputType.INPUT;
	
	String desCode() default "";
	
	boolean required() default false;
}
