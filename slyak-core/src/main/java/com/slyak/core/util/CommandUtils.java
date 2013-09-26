package com.slyak.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandUtils {

	public static void execute(String... commands) {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(commands);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(process!=null){
				process.destroy();
			}
		}
	}

	public static void main(String[] args) {
		execute("java -version");
	}
}
