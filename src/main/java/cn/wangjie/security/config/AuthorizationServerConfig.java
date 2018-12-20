package cn.wangjie.security.config;

import cn.wangjie.security.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-13 11:14
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter ;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("appID_wj")
                .secret(passwordEncoder.encode("appKey_wj"))
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(72000)
                //支持的授权模式
                .authorizedGrantTypes("authorization_code","password","refresh_token")
                .redirectUris("http://localhost:8081/")
                //令牌权限
                .scopes("all")
        .and()
                .withClient("appID_wj1")
                .secret(passwordEncoder.encode("appKey_wj1"))
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(72000)
                //支持的授权模式
                .authorizedGrantTypes("authorization_code","password","refresh_token")
                //令牌权限
                .scopes("all");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsServiceImpl);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

}
