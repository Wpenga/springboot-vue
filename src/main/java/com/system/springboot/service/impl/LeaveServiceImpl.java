package com.system.springboot.service.impl;

import com.system.springboot.entity.Leave;
import com.system.springboot.mapper.LeaveMapper;
import com.system.springboot.service.ILeaveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生请假表 服务实现类
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-04-18
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements ILeaveService {

}
