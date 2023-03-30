package com.system.springboot.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.system.springboot.UserService.IUserService;
import com.system.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtils {
    private  static IUserService staticUserService;
    @Resource
    private  IUserService userService;
    @PostConstruct
    public  void setUserService(){
        staticUserService = userService;
    }
    /**
     * 生成Token
     * @param userId
     * @param sign
     * @return
     */
    public static  String getToken(String userId,String sign){
        return JWT.create().withAudience(userId) // 将 userId 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     */
    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return  staticUserService.getById(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
        return  null;
    }

}
