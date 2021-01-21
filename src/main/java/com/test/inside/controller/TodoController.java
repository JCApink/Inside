package com.test.inside.controller;


import com.alibaba.fastjson.JSONObject;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.User;
import com.test.inside.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("todo")
@CrossOrigin
@RestController
public class TodoController {
    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private TodoService todoService;

    @RequestMapping(method = RequestMethod.GET)
    public String sortTodoTime(HttpServletRequest request){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer id = u.getId();
            DataMap dataMap = todoService.sortTodoTime(id);
            if(dataMap == null){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(CodeType.COMMENT_BLANK.getMessage(), CodeType.COMMENT_BLANK.getCode());
                return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
            }
            return JsonResult.build(dataMap).toJSON();
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(), CodeType.USER_NOT_LOGIN.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    private String setTodo(HttpServletRequest request,
                           @RequestBody String listCalendar){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();
            System.out.println(true);
            boolean flag = todoService.addTodo(userid, listCalendar);
            if(flag == false){
                return JsonResult.fail().toJSON();
            }
            return JsonResult.success().toJSON();
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(), CodeType.USER_NOT_LOGIN.getCode());
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
            boolean flag = todoService.deleteTodo(userid, id);
            if(flag == true){
                return JsonResult.success().toJSON();
            }
            return JsonResult.fail().toJSON();

        }
        else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(), CodeType.USER_NOT_LOGIN.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = {"delete"})
    public String deleteAllDateTime(HttpServletRequest request,
                                    @RequestParam(value = "delete") String delete){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null) {
            User u = (User) user;
            Integer userid = u.getId();
            todoService.deleteAllTodo(userid);
            return JsonResult.success().toJSON();
        }
        else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }
    }


}
