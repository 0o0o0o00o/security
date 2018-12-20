/**
 * 
 */
package cn.wangjie.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhailiang
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "imooc.security")
public class SecurityProperties {

	private String signUpUrl = "/signUp.html";
	private SocialProperties social = new SocialProperties();


}

