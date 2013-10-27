package org.matrix.web.component;

/**
 * 表示该对象可以渲染。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月13日 上午10:02:48
 */
@TemplateVariable
public interface Renderable {
	/**
	 * 渲染当前的对象.
	 * <p>
	 * 建议实现的时候将该方法返回的结果返回到{@link #toString()}
	 * 
	 * @return
	 */
	String render();

	/**
	 * 渲染当前的对象.
	 * 
	 * @return
	 */
	String toString();
}