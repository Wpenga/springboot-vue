package com.system.springboot.service;

import com.system.springboot.entity.Health;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.springboot.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-04-14
 */
public interface IHealthService extends IService<Health> {

    List<User> selectAllStudentAndRecord(String username);
}
