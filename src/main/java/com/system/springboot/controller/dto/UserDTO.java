package com.system.springboot.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.system.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

/*
 * 接受前端登录请求的参数
*/
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String address;
    private String token;
    private String role;
    private List<Menu> menus;
}
