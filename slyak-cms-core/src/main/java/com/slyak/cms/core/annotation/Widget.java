package com.slyak.cms.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Widget {

	String tag() default "";
	
	Setting[] settings() default {};
	
	String[] js() default {};
	
	String[] css() default {};
	
	String onAdd() default "";
	
	String onEdit() default "";
	
	String onRemove() default "";

	boolean show() default true;
}
