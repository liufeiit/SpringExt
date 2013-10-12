package org.springframework.ext.web.velocity.component;

/**
 * 静态资源访问控制器。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月1日 下午4:52:51
 */
public interface StaticResourceController extends Renderable {

	/**
	 * 设置静态资源名称。
	 * 
	 * @param resource
	 * @return
	 */
	StaticResourceController setTarget(String target);

	/**
	 * 设置静态资源根路径。
	 * 
	 * @param prefix
	 * @return
	 */
	StaticResourceController setPrefix(String prefix);

	/**
	 * 设置要访问的静态资源所在的服务器。
	 * 
	 * @param address
	 * @return
	 */
	StaticResourceController setServer(String server);
}