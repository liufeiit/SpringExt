package org.matrix.proxy;

import java.lang.reflect.Proxy;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月28日 下午10:53:06
 */
public class ProxyRunner {

	public static void main(String[] args) {
		TestMapper testMapper = (TestMapper) Proxy.newProxyInstance(ProxyRunner.class.getClassLoader(), new Class<?>[]{TestMapper.class}, new TestMapperProxy());
		
		System.out.println(testMapper.getName("刘 ", "飞"));
	}
}
