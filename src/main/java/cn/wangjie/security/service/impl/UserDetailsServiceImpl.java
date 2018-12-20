package cn.wangjie.security.service.impl;

import cn.wangjie.security.entity.po.UserPO;
import cn.wangjie.security.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-11 11:51
 **/
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService , SocialUserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPO userPO =new UserPO();
        userPO.setName(username);
        userPO = userMapper.selectOne(userPO);
        log.info("查到用户：{}", userPO);
        return new org.springframework.security.core.userdetails.User(username, userPO.getPassword(),true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("登录用户id:{}", userId);
        return getUserDetails(userId);
    }
    private SocialUser getUserDetails(String username) {
        String password = passwordEncoder.encode("123456");
        log.info("数据库密码{}", password);
        SocialUser admin = new SocialUser(username,
//                              "{noop}123456",
                password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return admin;
    }

}
