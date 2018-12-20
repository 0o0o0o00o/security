package cn.wangjie.security.entity.po;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-11 12:00
 **/
@Data
@Table(name = "user")
public class UserPO {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String name;
    private String password;
}
