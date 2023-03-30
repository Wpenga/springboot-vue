package com.system.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

//实体用户类
//该注解实现自动写getter，setter类，toString
@Data
@TableName("stu_user")
@ApiModel(value = "User对象")
//@ToString

public class User {
//    @TableId(type = IdType.AUTO)
//    private Integer id;
//    private String username;
//    @JsonIgnore
//    private String password;
//    private String nickname;
//    private String phone;
//    private String address;
//    private String sign;
//    private String avatarUrl;
//    private String createTime;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    @Alias("用户名") //hutool提供的别名注解工具
    private String username;

    @JsonIgnore
    @ApiModelProperty("密码")
    @Alias("密码")
    private String password;

    @ApiModelProperty("昵称")
    @Alias("昵称")
    private String nickname;

    @ApiModelProperty("地址")
    @Alias("地址")
    private String address;

    @ApiModelProperty("手机号")
    @Alias("手机号")
    private String phone;

    @ApiModelProperty("签到状态")
    @Alias("签到状态")
    private String sign;

    @ApiModelProperty("创建时间")
    @Alias("创建时间")
    private Date createTime;

    @ApiModelProperty("头像")
    @Alias("头像")
    private String avatarUrl;
}
