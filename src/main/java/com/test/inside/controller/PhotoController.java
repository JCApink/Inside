package com.test.inside.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;

import com.test.inside.model.pojo.Photo;
import com.test.inside.model.pojo.User;

import com.test.inside.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping(value = "/photowall",produces = "application/json;charset=UTF-8")
public class PhotoController {


    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private PhotoService photoService;

    //添加照片
    @RequestMapping(method = RequestMethod.POST )
    public String add(HttpServletRequest request,
                      @RequestBody Photo photo){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();
            boolean flag = photoService.add(photo,userid);
            if(flag == false){
                return JsonResult.fail().toJSON();
            }
            return JsonResult.success().toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }

    //获得照片组
    @RequestMapping(method = RequestMethod.GET )
    public String getPhotolist(HttpServletRequest request,int listid){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();
            if(listid>4||listid<1){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(CodeType.PHOTOLIST_OUT.getMessage(),CodeType.PHOTOLIST_OUT.getCode());
                return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
            }
            Map map = photoService.getPhotolist(userid,listid);

            //返回JSON格式数据
            DataMap<Object> dataMap = DataMap.success().setData(map);
            return JsonResult.build(dataMap).toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }

    @RequestMapping(method = RequestMethod.POST, params = {"id"})
    public String delPhoto(HttpServletRequest request,
                           @RequestParam(value = "id") Integer id){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();
            boolean flag = photoService.removeById(id);
            if(flag == false){
                return JsonResult.fail().toJSON();
            }
            return JsonResult.success().toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }


}
