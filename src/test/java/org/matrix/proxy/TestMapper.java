package org.matrix.proxy;

import org.matrix.orm.annotations.Parameter;
import org.matrix.orm.annotations.Query;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年10月28日 下午10:50:25
 */
public interface TestMapper {

	@Query(template="select * from usr;")
	String getName(
			@Parameter(name="firstName") String firstName, 
			@Parameter(name="secondName") String secondName);
}
