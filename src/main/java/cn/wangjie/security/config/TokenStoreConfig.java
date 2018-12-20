package cn.wangjie.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-13 15:48
 **/
@Configuration
public class TokenStoreConfig {


    @Configuration
    @ConditionalOnProperty(prefix = "security.oauth2", name = "tokenStore", havingValue = "redis")
    public static class RedisStoreConfig{

        @Autowired
        private LettuceConnectionFactory lettuceConnectionFactory;

        @Bean(name = "myTokenStore")
        public TokenStore redisTokenStore() {
            return new MyRedisTokenStore(lettuceConnectionFactory);
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = "security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
    public static class JwtConfig {

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey("jwt_sign");
            return converter;
        }

     /*   @Bean
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer(){
            return new ImoocJwtTokenEnhancer();
        }
*/
    }

}
