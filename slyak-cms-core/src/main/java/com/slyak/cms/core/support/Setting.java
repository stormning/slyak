package com.slyak.cms.core.support;

import java.lang.reflect.Method;

import com.slyak.cms.core.enums.InputType;

public class Setting {
	
	private String key;
	
	private String value;

	private String[] options;
	
	private Method optionsLoader;

	private InputType inputType;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public Method getOptionsLoader() {
		return optionsLoader;
	}

	public void setOptionsLoader(Method optionsLoader) {
		this.optionsLoader = optionsLoader;
	}

}
