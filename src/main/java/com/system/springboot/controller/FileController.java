package com.system.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.springboot.common.Result;
import com.system.springboot.entity.Files;
import com.system.springboot.mapper.FileMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传相关接口
 */
@Api(tags = "文件类操作")
@RestController
@RequestMapping("file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;  //读取上传路径

    @Value("${server.ip}")
    private String serverIp;

    @Resource
    private FileMapper fileMapper;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //定义一个文件的唯一标识码
        String uuid= IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath +fileUUID);

        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        //获取文件的url
        String url;
        //获取文件存储到磁盘目录
        file.transferTo(uploadFile);
        //获取文件md5，通过判断md5是否存在避免重复上传相同内容的文件
        String md5 = SecureUtil.md5(uploadFile);
        Files dbFiles = getFileByMd5(md5);


        if(dbFiles != null){
            url = dbFiles.getUrl();
            //文件存在，则删除，避免重复上传
            uploadFile.delete();
        }else {
//            //获取文件存储到磁盘目录
//            file.transferTo(uploadFile);
            url = "http://"+ serverIp + ":8090/file/"+fileUUID;
        }

        //存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
//        flushRedis(Constants.FILES_KEY);
        return url;
    }

    @ApiOperation("文件下载")
    @GetMapping("{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws  IOException{
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath +fileUUID);
        //设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过文件md5查询文件
     * @param md5
     * @return
     */
    private Files getFileByMd5(String md5){
        //查询文件的md5是否已存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper();
        queryWrapper.eq("md5",md5);
        return fileMapper.selectOne(queryWrapper);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        fileMapper.updateById(files);
//        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(fileMapper.selectById(id));
    }

    //清除一条缓存，key为要清空的数据
//    @CacheEvict(value="files",key="'frontAll'")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);
//        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        // select * from sys_file where id in (id,id,id...)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }

    /**
     * 分页查询接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("is_delete", false);
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }
}
