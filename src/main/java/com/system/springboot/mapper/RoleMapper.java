package com.system.springboot.mapper;

import com.system.springboot.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-03-30
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from sys_role where flag=#{role}")
    Integer selectByFlag(String flag);
}
