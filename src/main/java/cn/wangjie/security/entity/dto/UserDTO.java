package cn.wangjie.security.entity.dto;

import lombok.Data;


/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-11 12:00
 **/
@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String password;
}
