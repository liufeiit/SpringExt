package org.matrix.mybatis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年11月8日 下午11:43:30
 */
public class Runner {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:mybatis/applicationContext-mapper.xml");
		
		UserMapper mapper = context.getBean("userMapper", UserMapper.class);
		User usr = new User();
		usr.setExtend("mybatis");
		usr.setName("飞哥");
		usr.setPassword("lF1229lf");
		mapper.insertUser(usr);
		System.out.println("id: " + usr.getId());
		
		System.out.println(mapper.getUserById(626l));
		
	}
}
