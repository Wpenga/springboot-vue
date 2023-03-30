package com.system.springboot.UserService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.springboot.controller.dto.UserDTO;
import com.system.springboot.entity.User;
/*
* 服务类
* 定义各种接口，具体实现在实现类中
 */
public interface IUserService extends IService<User> {
    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);
//    boolean login(UserDTO userDTO);
}
