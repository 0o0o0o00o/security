/**
 * 
 */
package cn.wangjie.security.social;

import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author zhailiang
 *
 */
@Slf4j
@Component
public class MyConnectionSignUp implements ConnectionSignUp {

	@Override
	public String execute(Connection<?> connection) {
		//根据社交用户信息默认创建用户并返回用户唯一标识
		log.info("collection:{}",connection.toString());
		log.info("collection.key:{}",connection.getKey().toString());
		return connection.getDisplayName();
	}

}
