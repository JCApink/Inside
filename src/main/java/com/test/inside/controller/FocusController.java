package com.test.inside.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.inside.mapper.Mp3Mapper;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.Focus;
import com.test.inside.model.pojo.MP3Info;
import com.test.inside.model.pojo.User;
import com.test.inside.service.FocusService;
import com.test.inside.service.Mp3Service;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;


@CrossOrigin
@RestController
public class FocusController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    FocusService focusService;

    @GetMapping(value = "/getFocusTime",produces = "application/json;charset=UTF-8")
    public String getFocusTime(HttpServletRequest request){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();

            Focus focus = focusService.getFocusTime(userid);
            if(focus == null){
                return JsonResult.fail().toJSON();
            }
            //返回JSON格式数据
            DataMap<Object> dataMap = DataMap.success().setData(focus);
            return JsonResult.build(dataMap).toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();

    }

    @PostMapping("/addFocusTime")
    public String addFocusTime(HttpServletRequest request,int time){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();

            Boolean flag= focusService.addFocusTime(userid,time);
            if(flag == null){
                return JsonResult.fail().toJSON();
            }

            return JsonResult.success().toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();

    }

}
