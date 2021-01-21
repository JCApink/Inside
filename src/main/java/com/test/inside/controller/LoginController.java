package com.test.inside.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.entity.MD5;
import com.test.inside.model.pojo.User;
import com.test.inside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
@RequestMapping()
public class LoginController {

    @Autowired
    private UserService userService;

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @RequestMapping( value = "/login", method = RequestMethod.POST ,params = {"name", "password"})
    public String login( String name, String password){
        if(name == null){
            return JsonResult.fail(CodeType.USERNAME_BLANK).toJSON();
        }
        if(password == null){
            return JsonResult.fail(CodeType.PASSWORD_BLANK).toJSON();
        }
        DataMap dataMap = userService.login(name,password);
        if(dataMap == null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.NAME_OR_PASSWORD_ERROR.getMessage(),CodeType.NAME_OR_PASSWORD_ERROR.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }
        return JsonResult.build(dataMap).toJSON();

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, params = {"name", "password","email"})
    public String register( String name, String password, String email){
        boolean flag = userService.register(name,password,email);
        if(flag){
            return JsonResult.success().toJSON();
        }
        return JsonResult.fail().toJSON();

    }

    @RequestMapping(value = "/forgetpassword",method = RequestMethod.POST, params = {"name","email"})
    public String forget(String name, String email){
        System.out.println(name);
        DataMap dataMap = userService.forget(name, email);
        if(dataMap == null){
            return JsonResult.fail().toJSON();
        }
        return JsonResult.build(dataMap).toJSON();
    }

    @RequestMapping(value = "/forgetpassword",method = RequestMethod.POST, params = {"newpassword"})
    public String reset(HttpServletRequest request,String newpassword){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null) {
            User u = (User) user;
            boolean flag = userService.reset(u, newpassword);
            if (flag) {
                return JsonResult.success().toJSON();
            }else{
                return JsonResult.fail().toJSON();
            }
        }
        return JsonResult.fail().toJSON();
    }

    @RequestMapping(value = "/general",method = RequestMethod.POST, params = {"oldpassword","newpassword"})
    public String changepassword(HttpServletRequest request, String oldpassword, String newpassword){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null) {
            User u = (User) user;
            if(u.getPassword().equals(MD5.code(oldpassword))){
                userService.reset(u, newpassword);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(CodeType.PASSWORD_CHANGE.getMessage(),CodeType.PASSWORD_CHANGE.getCode());
                return JsonResult.build(DataMap.success().setData(jsonObject)).toJSON();
            }else{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(CodeType.PASSWORD_ERROR.getMessage(),CodeType.PASSWORD_ERROR.getCode());
                return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
            }
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }


    }



}