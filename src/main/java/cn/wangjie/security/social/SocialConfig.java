/**
 * 
 */
package cn.wangjie.security.social;


import cn.wangjie.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author zhailiang
 *
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired(required = false)
	private MyConnectionSignUp connectionSignUp;
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		MyJdbcUsersConnectionRepository repository = new MyJdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		repository.setTablePrefix("social_");
/*		if(connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}*/
		return repository;
	}
	
	@Bean
	public SpringSocialConfigurer mySpringSocialSecurityConfig() {
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		MySpringSocialConfigurer configurer = new MySpringSocialConfigurer(filterProcessesUrl);
		configurer.signupUrl(securityProperties.getSignUpUrl());
		return configurer;
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}


    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)) {
        };
    }
}
