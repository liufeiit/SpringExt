package org.springframework.ext.web.velocity.component;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

/**
 * 自定义扩展的模板controller
 * <p>
 * 用于直接解析一个control模板。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月1日 下午4:54:48
 */
public class VelocityTemplateController implements TemplateController {
	protected final Log log = LogFactory.getLog(getClass());
	/** 所有模板文件的根路径 */
	protected String templates = null;
	/** control模板文件的根路径 */
	protected String control = null;
	/** 加载control模板文件的编码格式 */
	protected String encoding;;
	/** 模板引擎 */
	protected VelocityEngine velocityEngine = null;
	/** 当前上下文 */
	protected Context context = null;
	/** 模板地址 */
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
		this.name = templates + control + name;
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
		if (velocityEngine == null) {
			return null;
		}
		return (encoding != null ? velocityEngine.getTemplate(name, encoding) : velocityEngine.getTemplate(name));
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