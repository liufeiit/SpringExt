package org.matrix.web.component;

/**
 * 静态资源访问控制器。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月13日 上午10:02:32
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