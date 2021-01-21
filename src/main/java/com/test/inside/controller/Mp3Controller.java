package com.test.inside.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.inside.mapper.Mp3Mapper;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.MP3Info;
import com.test.inside.model.pojo.User;
import com.test.inside.service.Mp3Service;
import com.test.inside.service.Photo_Service;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;


@CrossOrigin
@RestController
public class Mp3Controller {

    @Autowired
    Mp3Service mp3Service;

    @Autowired
    Mp3Mapper mp3Mapper;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping(value = "/addMP3",produces = "application/json;charset=UTF-8")
    public String add(@RequestParam(value = "file") MultipartFile file) throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
            Map map = mp3Service.add(file);//返回数据
            //数据封装
            //System.out.println(map);
            DataMap<Object> dataMap = DataMap.success().setData(map);
            return JsonResult.build(dataMap).toJSON();
    }

    @PostMapping(value = "/saveMP3",produces = "application/json;charset=UTF-8")
    public String saveMP3(HttpServletRequest request,@RequestParam(value = "name")String name) throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();
            MP3Info mp3Info = new MP3Info();

            mp3Info.setUserid(userid);
            mp3Info.setTitle(name);
            mp3Service.save(mp3Info);
            return JsonResult.success().toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }

    @PostMapping(value = "/getMP3",produces = "application/json;charset=UTF-8")
    public String getMP3(HttpServletRequest request) throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();

            Map map = mp3Service.get(userid);;//返回数据
            //数据封装
            //System.out.println(map);
            DataMap<Object> dataMap = DataMap.success().setData(map);
            return JsonResult.build(dataMap).toJSON();

        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }

    @PostMapping(value = "/delMP3",produces = "application/json;charset=UTF-8")
    public String delMP3(HttpServletRequest request,@RequestParam(value = "name") String name){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();
            Boolean flag = mp3Service.deleteByName(userid, name);
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
