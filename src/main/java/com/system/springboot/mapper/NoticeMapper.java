package com.system.springboot.mapper;

import com.system.springboot.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-04-12
 */
public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("select * from sys_notice where notice_id=#{notice_id}")
    Integer selectByNoticeId(Integer notice_id);
}
