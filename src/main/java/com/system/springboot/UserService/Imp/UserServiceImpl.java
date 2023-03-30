package com.system.springboot.UserService.Imp;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.springboot.UserService.IUserService;
import com.system.springboot.common.Constants;
import com.system.springboot.controller.dto.UserDTO;
import com.system.springboot.entity.User;
import com.system.springboot.exception.ServiceException;
import com.system.springboot.mapper.UserMapper;
import com.system.springboot.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

//服务实现类
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    private  static final Log LOG = Log.get();
    @Override
    public UserDTO login(UserDTO userDTO) {     //登录逻辑判断
        User one = getUserInfo(userDTO);
        //非空继续判断
        if (one != null) {
            //根据登录信息copy数据库对应的全部信息 one复制给userDTO
            BeanUtils.copyProperties(one, userDTO);
            //设置Token
            String token = TokenUtils.getToken(one.getId().toString(),one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {       //注册逻辑操作
        User one = getUserInfo(userDTO);
        if(one == null){
            one = new User();
            BeanUtils.copyProperties(userDTO,one);
            save(one);
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
        return one;
    }
    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); //查询数据库信息
        } catch (Exception e) {
            LOG.error(e);
            throw  new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }
}
   /* @Autowired
    private UserMapper userMapper;
      MyBatis
    public  int save(User user){
        if(user.getId() == null){   //判断新增还是更新
            return userMapper.insert(user);
        }
        else {  //更新
            return userMapper.update(user);
        }
    }*/
    //MyBatis Plus
    /*public  boolean saveUser(User user){
        if(user.getId() == null){   //判断新增还是更新
            return save(user);
        }
        else {  //更新
            return updateById(user);
        }
        return saveOrUpdate(user);
    }*/
