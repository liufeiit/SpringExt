package org.matrix.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月28日 下午10:58:05
 */
@Target(value = { ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

	public String name();
}