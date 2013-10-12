package org.springframework.ext.web.velocity.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 静态资源访问控制器。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月1日 下午4:57:02
 */
public class CommonStaticResourceController implements StaticResourceController {
	protected final Log log = LogFactory.getLog(getClass());
	protected String server = "";
	protected String prefix = "";
	protected String target = "";

	protected String source;

	protected void init() {
		if (StringUtils.isEmpty(server)) {
			throw new IllegalStateException("Property 'server' is required.");
		}
		if (StringUtils.isEmpty(prefix)) {
			log.warn("Property 'prefix' is Empty.");
		}
		if (StringUtils.isEmpty(target)) {
			throw new IllegalStateException("Property 'target' is required.");
		}

		source = server + prefix + target;
	}

	@Override
	public String render() {
		init();
		return source;
	}

	@Override
	public StaticResourceController setTarget(String target) {
		this.target = target;
		return this;
	}

	@Override
	public StaticResourceController setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	@Override
	public StaticResourceController setServer(String server) {
		this.server = server;
		return this;
	}

	@Override
	public String toString() {
		return render();
	}
}