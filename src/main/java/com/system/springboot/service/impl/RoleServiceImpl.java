package com.system.springboot.service.impl;

import com.system.springboot.entity.Role;
import com.system.springboot.mapper.RoleMapper;
import com.system.springboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-03-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
