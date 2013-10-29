package org.matrix.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;


//import org.springframework.cglib.proxy.Enhancer;
//import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.util.ClassUtils;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月29日 下午10:56:43
 */
public class CglibProxyTester {
	
	public static Object createProxy(Class<?> targetClass) {
		Enhancer enhancer = new Enhancer();
//		enhancer.setSuperclass(targetClass);
		/*enhancer.setCallback(new InvocationHandler() {
			@Override
			public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
				if("getName".equals(arg1.getName())) {
					return "" + arg2[0] + " " + arg2[1];
				} else if("nameB".equals(arg1.getName())) {
					return "nameB" + "" + arg2[0] + " " + arg2[1];
				} else if("nameC".equals(arg1.getName())) {
					return "nameC" + "" + arg2[0] + " " + arg2[1];
				}
				return null;
			}
		});*/
		enhancer.setCallback(new InvocationHandler(){
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if("getName".equals(method.getName())) {
					return "" + args[0] + " " + args[1];
				} else if("nameB".equals(method.getName())) {
					return "nameB" + "" + args[0] + " " + args[1];
				} else if("nameC".equals(method.getName())) {
					return "nameC" + "" + args[0] + " " + args[1];
				}
				return null;
			}});
		enhancer.setUseCache(true);
		enhancer.setInterfaces(ClassUtils.getAllInterfacesForClass(targetClass));
		// enhancer.setCallback(NoOp.INSTANCE);
		return enhancer.create();
	}
    
    public static interface A extends B, C {
    	String getName(String firstName, String secondName);
    	/*public String getName(String firstName, String secondName) {
    		return "liufei";
    	}*/

//		@Override
//		public String nameB(String firstName, String secondName) {
//			return null;
//		}
//
//		@Override
//		public String nameC(String firstName, String secondName) {
//			return null;
//		}
    }
    
    public static interface B {
    	String nameB(String firstName, String secondName);
    }
    
    public static interface C {
    	String nameC(String firstName, String secondName);
    }
    
    

	public static void main(String[] args) {
		A a = (A) createProxy(A.class);
		System.out.println(a.getName("刘", "飞"));
		System.out.println(a.nameB("刘", "飞"));
		System.out.println(a.nameC("刘", "飞"));
	}
}
