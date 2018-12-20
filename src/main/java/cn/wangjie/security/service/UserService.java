package cn.wangjie.security.service;

import cn.wangjie.security.entity.dto.UserDTO;
import cn.wangjie.security.entity.po.UserPO;

public interface UserService {
    UserPO register(UserDTO userDTO);
}
