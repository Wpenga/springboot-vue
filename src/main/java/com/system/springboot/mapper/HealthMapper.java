package com.system.springboot.mapper;

import com.system.springboot.entity.Health;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.springboot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-04-14
 */
public interface HealthMapper extends BaseMapper<Health> {
    // 查询学生及其打卡记录
    @Select("select s.*, r.id as record_id, r.punch_date, r.is_fever, r.go_risk, r.vaccine_count " +
            "from stu_user s left join stu_health r on s.username=r.username "+"WHERE s.username = #{username}")
    List<User> selectAllStudentAndRecord(@Param("username") String username);

    @Select("select * from stu_health where username=#{username}")
    Health getHealthByUser(String username);
}
