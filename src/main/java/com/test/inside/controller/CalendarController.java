package com.test.inside.controller;


import com.alibaba.fastjson.JSONObject;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.User;
import com.test.inside.service.CalendarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RequestMapping("calendar")
@CrossOrigin
@RestController
public class CalendarController {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    CalendarService calendarService;

    @RequestMapping(method = RequestMethod.POST)
    private String setCalendar(HttpServletRequest request,
                               @RequestBody String listCalendar){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();
            System.out.println(true);
            boolean flag = calendarService.addCanlendar(userid, listCalendar);
            if(flag == false){
                return JsonResult.fail().toJSON();
            }
            return JsonResult.success().toJSON();
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public String sortDateTime(HttpServletRequest request){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();
            DataMap dataMap = calendarService.sortDateTime(userid);
            if(dataMap == null){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(CodeType.COMMENT_BLANK.getMessage(),CodeType.COMMENT_BLANK.getCode());
                return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
            }
            return JsonResult.build(dataMap).toJSON();
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }

    }

    @RequestMapping(method = RequestMethod.POST, params = {"id"})
    public String deleteDateTime(HttpServletRequest request,
                                 @RequestParam(value = "id") Integer id){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null) {
            User u = (User) user;
            Integer userid = u.getId();
            boolean flag = calendarService.deleteCanlendar(id);
            if(flag == true){
                return JsonResult.success().toJSON();
            }
            return JsonResult.fail().toJSON();

        }
        else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }
    }

}
