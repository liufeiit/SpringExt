package org.matrix.tomcat;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since Oct 12, 2013 4:38:42 PM
 */
public class EmbeddedContextLoaderListener extends ContextLoaderListener {

    @Override
    protected WebApplicationContext createWebApplicationContext(ServletContext sc) {
        GenericWebApplicationContext context = new GenericWebApplicationContext(sc);
//        context.setParent(ApplicationContextProvider.getApplicationContext());
        return context;
    }

    @Override
    protected ApplicationContext loadParentContext(ServletContext servletContext) {
		return null;
//        return ApplicationContextProvider.getApplicationContext();
    }
}