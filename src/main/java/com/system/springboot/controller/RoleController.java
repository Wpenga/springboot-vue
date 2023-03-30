package com.system.springboot.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.system.springboot.service.IRoleService;
import com.system.springboot.entity.Role;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.annotation.Resource;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴泽鹏
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private IRoleService roleService;
    @ApiOperation(value = "新增或更新数据",notes = "根据id实现数据的更新")
    //新增或修改 @RequestBody将前台的数据映射成User对象
    @PostMapping
    public  boolean save(@RequestBody Role role){
        return roleService.saveOrUpdate(user);
    }
    //删除数据 @PathVariable表述请求地址必须是（/{id}）
    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public boolean delete(@PathVariable Integer id){
            return roleService.removeById(id);
    }
    @PostMapping("/del/batch")
    @ApiOperation("批量删除")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return roleService.removeByIds(ids);
    }
    //查询所有数据
    @GetMapping
    @ApiOperation(value = "获取所有数据")
    public List<User> findAll(){
            return roleService.list();
    }
    //查询单个数据
    @GetMapping("/id")
    @ApiOperation(value = "获取单个数据")
    public Role findAll(@PathVariable Integer id){
        return roleService.getById(id);
    }
    //分页查询
    // 路径 /user/page
    // @RequestParam 接受 pageNum=1&pageSize=10
    @GetMapping("/page")
    @ApiOperation("分页/模糊查询")
    public IPage<Role> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize
                                    ){
            IPage<Role> page = new Page<>(pageNum, pageSize);

            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        //多条件模糊查询
        return roleService.page(page,queryWrapper);
    }
}
