package cn.wangjie.security.service.impl;

import cn.wangjie.security.entity.dto.UserDTO;
import cn.wangjie.security.entity.po.UserPO;
import cn.wangjie.security.mapper.UserMapper;
import cn.wangjie.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-19 17:24
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserPO register(UserDTO userDTO) {
        UserPO userPO = new UserPO();
        userPO.setName(userDTO.getName());
        userPO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        int result = userMapper.insertSelective(userPO);
        if (result == 1) {
            return userPO;
        } else {
            throw new RuntimeException("注册失败");
        }
    }
}
