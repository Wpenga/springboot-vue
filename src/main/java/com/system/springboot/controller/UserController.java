package com.system.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.springboot.UserService.IUserService;
import com.system.springboot.common.Constants;
import com.system.springboot.common.Result;
import com.system.springboot.controller.dto.UserDTO;
import com.system.springboot.entity.User;
import com.system.springboot.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.ToString;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

//标识为接口
@RestController
//接口统一加前缀
@RequestMapping("/user")
@Api(tags = "用户类操作")
@ToString
public class UserController {
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
    @Resource
    private IUserService userService;

    @PostMapping("/login")
    @ApiOperation("登录请求")
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){//判断添加 1.不为null 2.长度不为0 3.不存在空格
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Result  register(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){//判断添加 1.不为null 2.长度不为0 3.不存在空格
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return  Result.success(userService.register(userDTO));
    }


    @ApiOperation(value = "新增或更新数据",notes = "根据id实现数据的更新")
    //新增或修改 @RequestBody将前台的数据映射成User对象
    @PostMapping
    public  Result save(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));
    }

    //删除数据 @PathVariable表述请求地址必须是（/{id}）
    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public Result delete(@PathVariable Integer id){
        return Result.success(userService.removeById(id));
                //userMapper.deleteById(id);
    }
    @PostMapping("/del/batch")
    @ApiOperation("批量删除")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeByIds(ids));
        //userMapper.deleteById(id);
    }

    //查询单个数据 根据id
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个数据")
    public Result findOne(@PathVariable Integer id){
        return Result.success(userService.getById(id));
    }

    //查询单个数据 根据用户名
    @GetMapping("/username/{username}")
    @ApiOperation(value = "获取所有数据")
    public Result findOne(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return  Result.success(userService.getOne(queryWrapper));
    }

    //查询所有数据
    @GetMapping
    @ApiOperation(value = "获取所有数据")
    public Result findAll(){
//        List<User> all = userMapper.selectList(null);
//        return userService.list();
        return Result.success(userService.list());
    }
    //分页查询
    // 路径 /user/page
    // @RequestParam 接受 pageNum=1&pageSize=10
    @GetMapping("/page")
    @ApiOperation("分页/模糊查询")
/*    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam String username){
        pageNum = (pageNum - 1 ) * pageSize;
        List<User> data = userMapper.selectPage(pageNum, pageSize, username);
        Integer total = userMapper.selectTotal(username );
        //封装 key-value
        Map<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;*/
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String address,
                                @RequestParam(defaultValue = "") String  nickname){
        // MyBatis Plus
        IPage<User> page = new Page<>(pageNum, pageSize);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //多条件模糊查询
        queryWrapper.like(Strings.isNotEmpty(username),"username",username);
        queryWrapper.like(Strings.isNotEmpty(nickname),"nickname",nickname);
        queryWrapper.like(Strings.isNotEmpty(address),"address",address);
        queryWrapper.orderByDesc("id");

        //获取当前用户信息
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("获取当前用户信息========"+currentUser.getNickname());

        return  Result.success(userService.page(page,queryWrapper));
//        return userService.page(page,queryWrapper);
    }

    //导出
    @GetMapping("/export")
    @ApiOperation("下载")
    public void export(HttpServletResponse response) throws Exception{
        List<User> list =userService.list();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义标题别名
//        writer.addHeaderAlias("username", "用户名");
//        writer.addHeaderAlias("password", "密码");
//        writer.addHeaderAlias("nickname", "昵称");
//        writer.addHeaderAlias("address", "地址");
//        writer.addHeaderAlias("phone", "手机号");
//        writer.addHeaderAlias("sign", "签到状态");
//        writer.addHeaderAlias("avatarUrl", "签到状态");
//        writer.addHeaderAlias("createTime", "创建时间");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //文件名
        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        //输出流
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        // 关闭writer，释放内存
        writer.close();
    }
    //导入
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception{
        //获取输入流
        InputStream inputStream = file.getInputStream();
        //通过插件提供的方法读取输入流
        ExcelReader reader = ExcelUtil.getReader(inputStream);

//        reader.addHeaderAlias("username", "用户名");
//        reader.addHeaderAlias("password", "密码");
//        reader.addHeaderAlias("nickname", "昵称");
//        reader.addHeaderAlias("address", "地址");
//        reader.addHeaderAlias("phone", "手机号");
//        reader.addHeaderAlias("sign", "签到状态");
//        reader.addHeaderAlias("avatarUrl", "签到状态");
//        reader.addHeaderAlias("createTime", "创建时间");
        //读取的类型为泛型User
        List<User> list = reader.readAll(User.class);


//        List<User> list = reader.read(0,1,User.class);
        //数据批量保存到数据库
        userService.saveBatch(list);
        System.out.println(list);
//        return "导入成功";
        return Result.success(true);
    }
}
