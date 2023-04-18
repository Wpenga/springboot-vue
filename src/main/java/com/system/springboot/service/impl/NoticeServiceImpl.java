package com.system.springboot.service.impl;

import com.system.springboot.entity.Notice;
import com.system.springboot.mapper.NoticeMapper;
import com.system.springboot.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-04-12
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
