package cn.wangjie.security.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @program: redisson
 * @description:
 * @author: WangJie
 * @create: 2018-11-30 16:27
 **/
@Configuration
public class RedissonConfig {

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {
        String[] profiles = env.getActiveProfiles();
        String profile = "";
        if(profiles.length > 0) {
            profile = "-" + profiles[0];
        }
        return Redisson.create(
                Config.fromYAML(new ClassPathResource("redisson" + profile + ".yml").getInputStream())
        );
    }

}
