package com.slyak.cms.widgets.gather;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSGetter;

public class ResultHandler extends ScriptableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String result;

	public ResultHandler() {
	}

	@JSConstructor
	public ResultHandler(String result) {
		this.result = result;
	}

	@Override
	public String getClassName() {
		return "ResultHandler";
	}

	@JSGetter
	public String getResult() {
		System.out.println("result is============================>");
		System.out.println(result);
		return result;
	}
}