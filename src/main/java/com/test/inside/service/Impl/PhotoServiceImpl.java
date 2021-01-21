package com.test.inside.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.PhotoMapper;
import com.test.inside.mapper.PhotogroupMapper;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.Photo;
import com.test.inside.model.pojo.Photogroup;
import com.test.inside.service.PhotoService;
import com.test.inside.service.PhotogroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

    @Autowired
    PhotoService photoService;

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    PhotogroupService photogroupService;

    @Autowired
    PhotogroupMapper photogroupMapper;


    @Override
    public boolean add(Photo photo,int userid){
        String listtitle=photoMapper.getListtitleById(photo.getListid(),userid);
        System.out.println(listtitle);
        photo.setUserid(userid);
        System.out.println(photo.toString());
        photoService.saveOrUpdate(photo);
        return true;
    }

    @Override
    public Map getPhotolist(Integer userid, int listid) {
        //判断是否有照片分组
        Boolean flag = photogroupService.Initialize(userid);
        if(!flag){
            //没有照片分组，自动创建默认分组
            System.out.println("图片分组为空,创建默认分组");
            photogroupService.createNewPhotoGroup(userid);
        }
        if(listid>4){
            return null;
        }
        Photo[] photos = photoMapper.getByListid(listid,userid);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("urllist",photos);
        String listtitle = photogroupMapper.getListtitle(listid,userid);
        map.put("listid",listid);
        map.put("listtitle",listtitle);

        map.put("imageSizeLimit",5);
        map.put("imageNumLimit", JsonResult.PHOTO_SIZE_LIMIT[listid]);
        return map;
    }

}
