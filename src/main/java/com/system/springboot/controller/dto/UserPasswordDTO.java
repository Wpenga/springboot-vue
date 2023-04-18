package com.system.springboot.controller.dto;

import lombok.Data;

/**
 * 修改密码
 */
@Data
public class UserPasswordDTO {
    private String username;
    private String password;
    private String newPassword;
}