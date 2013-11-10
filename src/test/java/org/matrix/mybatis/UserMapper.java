package org.matrix.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年11月8日 下午11:31:54
 */
public interface UserMapper {

	@Insert(value="INSERT INTO usr_info(name, password, extend)VALUES(#{name}, #{password}, #{extend});")
	void insertUser(User user);
	@Select(value="SELECT id, name, password, extend from usr_info where id = #{id};")
	User getUserById(@Param(value="id") long id);
}
