package com.test.inside.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.Photo_Mapper;
import com.test.inside.model.pojo.Photo_;
import com.test.inside.service.Photo_Service;
import com.test.inside.util.Topingyin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class Photo_ServiceImpl extends ServiceImpl<Photo_Mapper, Photo_> implements Photo_Service {

    @Autowired
    Photo_Service photo_service;

    @Autowired
    Photo_Mapper photo_mapper;



    @Override
    public Map add(MultipartFile upload){
        //String filePath = "D:\\study\\springboot\\inside\\photos\\";//测试
        String filePath = "/usr/springboot/photos/photo/";
        //判断文件夹是否存在,不存在则创建
        File file=new File(filePath);
        Photo_ photo_ = new Photo_();
        Map<String,Object> map = new HashMap<String,Object>();
        if(!file.exists()){
            System.out.println("创建新文件夹"+filePath);
            file.mkdirs();
        }
        String originalFileName = upload.getOriginalFilename();//获取原始图片的扩展名
        String newFileName = Topingyin.trans2PinYin(originalFileName);//将名字转化为拼音
        String newFilePath=filePath+newFileName; //新文件的路径
        System.out.println(newFilePath);

        try {
            upload.transferTo(new File(newFilePath));//将传来的文件写入新建的文件
            //返回数据
            photo_.setName(newFileName);
            photo_.setUrl("http://120.79.195.7:8081/images/photo/"+newFileName);

            map.put("photo",photo_);
            photo_service.save(photo_);
            System.out.println("上传图片成功进行上传文件测试");
            return map;
        }catch (IllegalStateException e ) {
            //处理异常
        }catch(IOException e1){
            //处理异常
        }
        return null;
    }

    @Override
    public Boolean del(@PathVariable("url") String name,Integer userid){

        String url = "D:\\study\\springboot\\inside\\photos\\user_"+userid+"\\"+name;//测试
        //String url = "/usr/springboot/photos/user_"+userid+"/"+name;

        File file = new File(url);
        if (file.exists()){//文件是否存在
            file.delete();//删除文件
            System.out.println("删除文件成功");
            photo_mapper.deleteByName(name);
            return true;
        }
        else {
            System.out.println("文件不存在");
            return false;
        }
    }



}
