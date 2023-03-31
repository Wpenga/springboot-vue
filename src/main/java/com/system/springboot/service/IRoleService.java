package com.system.springboot.service;

import com.system.springboot.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.springboot.entity.RoleMenu;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-03-30
 */
public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
