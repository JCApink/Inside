package com.test.inside.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.Photo_;
import com.test.inside.model.pojo.User;
import com.test.inside.service.PhotoService;
import com.test.inside.service.Photo_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;


@CrossOrigin
@RestController
public class Photo_Controller {

    @Autowired
    Photo_Service photo_Service;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping(value = "/addphoto",produces = "application/json;charset=UTF-8")
    public String add(
                      @RequestParam(value = "file") MultipartFile file) throws UnsupportedEncodingException {
            Map map = photo_Service.add(file);//返回数据
            //数据封装
            System.out.println(map);
            DataMap<Object> dataMap = DataMap.success().setData(map);
            return JsonResult.build(dataMap).toJSON();

    }

    @PostMapping(value = "/delphoto",produces = "application/json;charset=UTF-8")
    public String del(HttpServletRequest request,
                      @RequestParam(value = "name") String  name) throws UnsupportedEncodingException {
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();

            Boolean flag = photo_Service.del(name,userid);//返回数据
            //数据封装
            if(flag)
                return JsonResult.success(CodeType.DEL_PHOTO_SUCCESS.getMessage()).toJSON();
            else
                return JsonResult.fail(CodeType.DEL_PHOTO_FAIL.getMessage()).toJSON();


        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }
}
