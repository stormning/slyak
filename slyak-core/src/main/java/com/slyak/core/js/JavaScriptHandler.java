/**
 * Project name : slyak-core
 * File name : JavaScriptHandler.java
 * Package name : com.slyak.core.js
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.js;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class JavaScriptHandler implements InitializingBean {

	private Resource[] jsLibs = new Resource[] {new ClassPathResource("/com/slyak/core/js/env.rhino.1.2.js"),new ClassPathResource("/com/slyak/core/js/jquery.js")};

	private Scriptable global;

	private Cache funtionCache = new ConcurrentMapCache(getClass().getName()
			+ "#function");
	private Cache scriptCache = new ConcurrentMapCache(getClass().getName()
			+ "#script");

	public Object evalScript(Map<String, Object> args, String... scriptStrings) {
		if (scriptStrings == null || scriptStrings.length == 0) {
			return null;
		}
		Context ctx = Context.enter();
		try {
			ctx.setOptimizationLevel(-1);
			Scriptable scope = new NativeObject();
			scope.setParentScope(global);
			setArgs(args, scope);
			for (int i = 0; i < scriptStrings.length; i++) {
				Script script = compileIfNotCompiled(scriptStrings[i], ctx);
				Object result = script.exec(ctx, scope);
				if (i == scriptStrings.length - 1) {
					return result;
				}
			}
			return null;
		} finally {
			Context.exit();
		}
	}

	public Object evalFunction(String functionStr, Object... args) {
		Context ctx = Context.enter();
		try {
			Scriptable scope = new NativeObject();
			scope.setParentScope(global);
			Function func = compileIfNotCompiled(ctx, functionStr);
			return func.call(ctx, scope, null, args);
		} finally {
			Context.exit();
		}
	}

	private Function compileIfNotCompiled(Context ctx, String funcStr) {
		ValueWrapper vw = funtionCache.get(funcStr);
		Function func = null;
		if (vw == null) {
			func = ctx.compileFunction(global, funcStr, null, 1, null);
			funtionCache.put(funcStr, func);
		} else {
			func = (Function) vw.get();
		}
		return func;
	}

	private void setArgs(Map<String, Object> args, Scriptable scriptable) {
		if (!CollectionUtils.isEmpty(args)) {
			Iterator<String> propNameIt = args.keySet().iterator();
			while (propNameIt.hasNext()) {
				String name = propNameIt.next();
				ScriptableObject.putProperty(scriptable, name, args.get(name));
			}
		}
	}

	private Script compileIfNotCompiled(String scriptString, Context ctx) {
		ValueWrapper vw = scriptCache.get(scriptString);
		Script script = null;
		if (vw == null) {
			script = ctx.compileString(scriptString, UUID.randomUUID()
					.toString(), 0, null);
			scriptCache.put(scriptString, script);
		} else {
			script = (Script) vw.get();
		}
		return script;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Context ctx = Context.enter();
		try {
			ctx.setOptimizationLevel(-1);
			global = ctx.initStandardObjects();
			for (Resource res : jsLibs) {
				ctx.evaluateReader(global,
						new InputStreamReader(res.getInputStream()),
						res.getFilename(), 0, null);
			}
		} finally {
			Context.exit();
		}
	}

	public void setJsLibs(Resource[] jsLibs) {
		this.jsLibs = jsLibs;
	}

	public static void main(String[] args) {
		JavaScriptHandler handler = new JavaScriptHandler();
		try {
			handler.afterPropertiesSet();
			Object obj = handler.evalFunction("function(str){return $(str).find(':not(span)').html()}","<div><span>aaaa</span><b>bb</b></div><script>alert(1)</script>");
			System.out.println(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
