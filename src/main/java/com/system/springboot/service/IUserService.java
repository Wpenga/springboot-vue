package com.system.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.springboot.controller.dto.UserDTO;
import com.system.springboot.controller.dto.UserPasswordDTO;
import com.system.springboot.entity.User;
/*
* 服务类
* 定义各种接口，具体实现在实现类中
 */
public interface IUserService extends IService<User> {
    //登录
    UserDTO login(UserDTO userDTO);
    //注册
    User register(UserDTO userDTO);
    //修改密码
    void updatePassword(UserPasswordDTO userPasswordDTO);

//    Object selectAllStudentAndRecord(String username);

//    boolean login(UserDTO userDTO);
}
