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
//    @Select("select s.*, r.id as record_id, r.punch_date as punchDate, r.is_fever, r.go_risk, r.vaccine_count " +
//            "from stu_user s left join stu_health r on s.username=r.username "+"WHERE s.username = #{username}")
    List<User> selectAllStudentAndRecord(@Param("username") String username);

    //获取用户打卡信息，联表查询获取用户昵称
    @Select("select stu_health.*,sys_user.nickname from stu_health left join sys_user on stu_health.username = sys_user.username "
            +"where stu_health.username=#{username}  ORDER BY id DESC LIMIT 1")
    Health getHealthByUser(String username);

    @Select("SELECT COUNT(*) FROM stu_health WHERE id IN (\n" +
            "  SELECT MAX(id)\n" +
            "  FROM stu_health\n" +
            "  GROUP BY username\n" +
            ") AND is_fever = 1;")
    int isFeverCount();

    @Select("SELECT * FROM stu_health WHERE id IN\n" +
            "    (SELECT MAX(id) FROM stu_health  GROUP BY username);")
    List<Health> getisSign();
}
