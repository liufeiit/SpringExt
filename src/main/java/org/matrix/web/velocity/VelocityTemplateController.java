package org.matrix.web.velocity;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.matrix.web.component.TemplateController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

/**
 * 自定义扩展的模板controller
 * <p>
 * 用于直接解析一个control模板。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月13日 上午10:03:32
 */
public class VelocityTemplateController extends WebApplicationObjectSupport implements TemplateController, InitializingBean, ComponentConstants {
	protected final Log log = LogFactory.getLog(getClass());
	/** 所有模板文件的根路径 */
	protected String templates = null;
	/** control模板文件的根路径 */
	protected String control = null;
	/** 加载control模板文件的编码格式 */
	protected String encoding = null;
	/** 模板引擎 */
	protected VelocityEngine velocityEngine = null;
	/** 当前上下文 */
	protected Context context = null;
	/** 模板名称，模板的绝对路径：templates +　control + name*/
	protected String name = null;

	@Override
	public String render() {
		try {
			Template controlTemplate = getTemplate(name);
			if (controlTemplate == null) {
				return null;
			}
			StringWriter sw = new StringWriter();
			controlTemplate.merge(context, sw);
			return sw.toString();
		} catch (Exception e) {
			log.error("render template named: " + name, e);
			return e.getLocalizedMessage();
		}
	}

	@Override
	public TemplateController setTarget(String name) {
		this.name = StringUtils.defaultIfBlank(templates, "") + StringUtils.defaultIfBlank(control, "") + name;
		if (log.isDebugEnabled()) {
			log.debug("setting control [" + this.name + "]");
		}
		return this;
	}

	@Override
	public TemplateController setQueryData(String key, Object value) {
		if (context == null) {
			context = new VelocityContext();
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("setting control [%s] QueryData [%s=%s]", name, key, value));
		}
		context.put(key, value);
		return this;
	}

	/**
	 * Retrieve the Velocity template specified by the given name, using the
	 * encoding specified by the "encoding" bean property.
	 * 
	 * @param name
	 *            the file name of the desired template
	 * @return the Velocity template
	 * @throws Exception
	 *             if thrown by Velocity
	 * @see org.apache.velocity.app.VelocityEngine#getTemplate
	 */
	protected Template getTemplate(String name) throws Exception {
		return (encoding != null ? velocityEngine.getTemplate(name, encoding) : velocityEngine.getTemplate(name));
	}
	
	/**
 	 * Invoked on startup. Looks for a single VelocityConfig bean to
 	 * find the relevant VelocityEngine for this factory.
 	 */
	@Override
	protected void initApplicationContext() throws BeansException {
		super.initApplicationContext();
		if (velocityEngine == null) {
			// No explicit VelocityEngine: try to autodetect one.
			setVelocityEngine(autodetectVelocityEngine());
		}
	}

	/**
	 * Autodetect a VelocityEngine via the ApplicationContext.
	 * Called if no explicit VelocityEngine has been specified.
	 * @return the VelocityEngine to use for VelocityViews
	 * @throws BeansException if no VelocityEngine could be found
	 * @see #getApplicationContext
	 * @see #setVelocityEngine
	 */
	protected VelocityEngine autodetectVelocityEngine() throws BeansException {
		try {
			VelocityConfig velocityConfig = BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(),
					VelocityConfig.class, true, false);
			return velocityConfig.getVelocityEngine();
		} catch (NoSuchBeanDefinitionException ex) {
			throw new ApplicationContextException(
					"Must define a single VelocityConfig bean in this web application context "
							+ "(may be inherited): VelocityConfigurer is the usual implementation. "
							+ "This bean may be given any name.", ex);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isEmpty(templates)) {
			templates = DEFAULT_TEMPLATES;
		}
		if (StringUtils.isEmpty(encoding)) {
			encoding = DEFAULT_CHARSET;
		}
	}

	@Override
	public String toString() {
		return render();
	}
	
	/**
	 * @param templates the templates to set
	 */
	public void setTemplates(String templates) {
		this.templates = templates;
	}

	/**
	 * @param control
	 *            the control to set
	 */
	public void setControl(String control) {
		this.control = control;
	}

	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @param velocityEngine
	 *            the velocityEngine to set
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}
}