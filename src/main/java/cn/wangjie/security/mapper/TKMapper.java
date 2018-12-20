package cn.wangjie.security.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @program: security
 * @description:
 * @author: WangJie
 * @create: 2018-12-11 11:59
 **/
public interface TKMapper<T> extends Mapper<T> , MySqlMapper<T> {
}
