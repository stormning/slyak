package com.slyak.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Sample {
	
	public static void main(String[] args) {
		final HelloService hs = new HelloServiceImpl();
		Object proxyBean = Proxy.newProxyInstance(Sample.class.getClassLoader(),hs.getClass().getInterfaces(),new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return (String)method.invoke(hs, args)+"-->By zn";
			}
		});
		System.out.println(((HelloService)proxyBean).sayHello());
	}
}
