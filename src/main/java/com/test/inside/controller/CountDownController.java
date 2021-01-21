package com.test.inside.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.User;
import com.test.inside.service.CountDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-14
 */

@RequestMapping(value = "countdown")
@CrossOrigin
@RestController
public class CountDownController {

    @Resource
    RedisTemplate<String,Object> redisTemplate;


    @Autowired
    private CountDownService CountDownService;

    @RequestMapping(method = RequestMethod.GET)
    public String sortDateTime(HttpServletRequest request){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer id = u.getId();
            DataMap dataMap = CountDownService.sortDateTime(id);
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



    @RequestMapping(method = RequestMethod.POST, params = {"date", "content" , "title"})
    public String setDateTime(HttpServletRequest request,
                              @RequestParam(value = "date") String date,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "title") String title){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();
            boolean flag = CountDownService.setDateTime(userid, date, title,content);
            if(flag == true){
                return JsonResult.success().toJSON();
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.ADD_CALENDAR_FAILED.getMessage(), CodeType.ADD_CALENDAR_FAILED.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }
        else{
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
            boolean flag = CountDownService.deleteDateTime(userid, id);
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



}

