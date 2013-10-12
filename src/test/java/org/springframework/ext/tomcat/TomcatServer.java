package org.springframework.ext.tomcat;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since Oct 12, 2013 4:26:04 PM
 */
public class TomcatServer {
	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		File base = new File("src/main/resources");
		System.out.println(base.getAbsolutePath());
		Context rootCtx = tomcat.addContext("", base.getAbsolutePath());
		AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();
		annotationConfigWebApplicationContext.scan("org.springframework.ext.tomcat");
		annotationConfigWebApplicationContext.register(MvcConfig.class);
		DispatcherServlet dispatcher = new DispatcherServlet(annotationConfigWebApplicationContext);
		Tomcat.addServlet(rootCtx, "SpringMVC", dispatcher);
		rootCtx.addServletMapping("/*", "SpringMVC");
		tomcat.start();
	}
}
