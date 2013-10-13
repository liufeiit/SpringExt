package org.springframework.ext.web.velocity.component;

/**
 * 自定义扩展的模板controller
 * <p>
 * 用于直接解析一个control模板。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月13日 上午10:03:02
 */
public interface TemplateController extends Renderable {
	/**
	 * 设置control模板名称.
	 * 
	 * @param template
	 * @return
	 */
	TemplateController setTarget(String template);

	/**
	 * 为当前的control模板设置参数.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	TemplateController setQueryData(String key, Object value);
}