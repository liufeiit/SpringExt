package org.matrix.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.matrix.orm.annotations.Statement;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月28日 下午10:52:12
 */
public class TestMapperProxy implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Statement statement = method.getAnnotation(Statement.class);
		System.out.println("statement : " + statement.template());
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		System.out.println("method : " + method);
		System.out.println(method.getGenericParameterTypes());
		System.out.println(method.getGenericReturnType());
		System.out.println(method.getParameterTypes());
		System.out.println(method.getReturnType());
		System.out.println(method.getTypeParameters());
		return args[0] + (args[1] + "");
	}
}
