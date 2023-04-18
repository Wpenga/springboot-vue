package com.system.springboot.service.impl;

import com.system.springboot.entity.Health;
import com.system.springboot.entity.User;
import com.system.springboot.mapper.HealthMapper;
import com.system.springboot.mapper.UserMapper;
import com.system.springboot.service.IHealthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-04-14
 */
@Service
public class HealthServiceImpl extends ServiceImpl<HealthMapper, Health> implements IHealthService {
    @Resource
    private HealthMapper healthMapper;
    @Override
    public List<User> selectAllStudentAndRecord(String username) {
        return healthMapper.selectAllStudentAndRecord(username);
    }
}
