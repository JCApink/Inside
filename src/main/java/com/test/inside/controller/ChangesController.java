package com.test.inside.controller;


import com.alibaba.fastjson.JSONObject;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.Changes_array;
import com.test.inside.model.pojo.Sentence;
import com.test.inside.model.pojo.User;
import com.test.inside.service.ChangesService;
import com.test.inside.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-25
 */
@Controller
@CrossOrigin
@ResponseBody
public class ChangesController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ChangesService changesService;

    //句子提交
    @PostMapping(value = "/subChanges",produces = "application/json;charset=UTF-8")
    public String subChanges(HttpServletRequest request, @RequestBody List<Changes_array> changes_arrays) {
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();

            Boolean flag = changesService.subChanges(userid,changes_arrays);
            if(flag==false){
                return JsonResult.fail().toJSON();
            }
            return JsonResult.success().toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }

    //句子提交
    @GetMapping(value = "/getChanges",produces = "application/json;charset=UTF-8")
    public String getChanges(HttpServletRequest request) {
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();

            Map map = changesService.getChanges(userid);//返回数据
            if(map==null){
                return JsonResult.fail().toJSON();
            }
            // 数据封装
            //System.out.println(map);
            DataMap<Object> dataMap = DataMap.success().setData(map);
            return JsonResult.build(dataMap).toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }
}

