package org.springframework.ext.velocity;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

/**
 * https://www.ibm.com/developerworks/cn/java/j-lo-velocity/
 * 
 * Velocity模板上用于控制缓存的指令
 * 
 * Directive 是所有指令的基类，Directive 是一个抽象类，它有三个方法必须实现的，分别是：

    getName：返回指令的名称
    getType：返回指令的类型，行指令：LINE、块指令：BLOCK
    render：指令执行的入口

其中 render 方法的最后一个参数 node 表示为该指定对应在 Velocity 模板中的节点对象，通过调用 node 的 jjtGetChild 方法可以获取到传递给该指令的参数以及包含在该指令的脚本内容。

上面的代码中，首先获取传递给指令的参数，也就是缓存的区域名和对应缓存数据的键值。接着判断距上次数据被缓存时，指令所包含的脚本代码是否有更改（以便页面开发人员修改了 vm 脚本时自动刷新缓存数据），然后判断缓存中是否已有数据。当缓存中无数据或者页面代码被修改时，重新执行块指令中的脚本并将执行的结果置入缓存，否则直接将缓存中的数据输出到页面。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月15日 上午12:22:34
 */
public class CacheDirective extends Directive {

	final static Hashtable<String, String> body_tpls = new Hashtable<String, String>();

	@Override
	public String getName() {
		return "cache";
	} // 指定指令的名称

	@Override
	public int getType() {
		return BLOCK;
	} // 指定指令类型为块指令

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#render()
	 */
	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException,
			ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		// 获得缓存信息
		SimpleNode sn_region = (SimpleNode) node.jjtGetChild(0);
		String region = (String) sn_region.value(context);
		SimpleNode sn_key = (SimpleNode) node.jjtGetChild(1);
		Serializable key = (Serializable) sn_key.value(context);

		Node body = node.jjtGetChild(2);
		// 检查内容是否有变化
		String tpl_key = key + "@" + region;
		String body_tpl = body.literal();
		String old_body_tpl = body_tpls.get(tpl_key);
//		String cache_html = CacheHelper.get(String.class, region, key);
		String cache_html = "";
		if (cache_html == null || !StringUtils.equals(body_tpl, old_body_tpl)) {
			StringWriter sw = new StringWriter();
			body.render(context, sw);
			cache_html = sw.toString();
//			CacheHelper.set(region, key, cache_html);
//			CacheHelper.set(region, key, cache_html);
			body_tpls.put(tpl_key, body_tpl);
		}
		writer.write(cache_html);
		return true;
	}
}