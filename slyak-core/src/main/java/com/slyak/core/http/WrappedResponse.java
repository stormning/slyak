/**
 * Project name : slyak-core
 * File name : WrappedResponse.java
 * Package name : com.slyak.core.http
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;

public class WrappedResponse extends HttpServletResponseWrapper {
	private WrappedPrintWriter tmpWriter;

	private ByteArrayOutputStream output;
	
	private String encoding;

	public WrappedResponse(HttpServletResponse httpServletResponse) {
		super(httpServletResponse);
		encoding = httpServletResponse.getCharacterEncoding();
		output = new ByteArrayOutputStream();
		tmpWriter = new WrappedPrintWriter(output);
	}

	public void finalize() throws Throwable {
		super.finalize();
		output.close();
		tmpWriter.close();
	}

	public String getContent() {
		try {
			tmpWriter.flush();
			return tmpWriter.getByteArrayOutputStream().toString(encoding);
		} catch (UnsupportedEncodingException e) {
			return StringUtils.EMPTY;
		}
	}

	public PrintWriter getWriter() throws IOException {
		return tmpWriter;
	}

	public void close() throws IOException {
		tmpWriter.close();
	}

	private class WrappedPrintWriter extends PrintWriter {
		ByteArrayOutputStream myOutput;

		public WrappedPrintWriter(ByteArrayOutputStream output) {
			super(output);
			myOutput = output;
		}

		public ByteArrayOutputStream getByteArrayOutputStream() {
			return myOutput;
		}
	}
}
