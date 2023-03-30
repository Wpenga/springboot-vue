package com.system.springboot.service.impl;

import com.system.springboot.entity.Menu;
import com.system.springboot.mapper.MenuMapper;
import com.system.springboot.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
