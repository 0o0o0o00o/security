package cn.wangjie.security.mapper;

import cn.wangjie.security.entity.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends TKMapper<UserPO> {
}
