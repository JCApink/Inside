package com.test.inside.controller;


import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.Sentence;
import com.test.inside.model.pojo.User;
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
 * @since 2020-07-24
 */
@Controller
@CrossOrigin
@ResponseBody
public class SentenceController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SentenceService sentenceService;

    //句子提交
    @PostMapping(value = "/submitSentence",produces = "application/json;charset=UTF-8")
    public String subSentence(@RequestBody List<Sentence> value, HttpServletRequest request){
        //测试数据
        //value = new String[]{"123", "321", "213"};

        // /提交
        Map map = sentenceService.submit(value, request);

        DataMap<Object> dataMap = DataMap.success().setData(map);
        return JsonResult.build(dataMap).toJSON();
    }

    //句子请求
    @CrossOrigin
    @GetMapping(value = "/getSentence",produces = "application/json;charset=UTF-8")
    public  String  getSentence(HttpServletRequest request){
        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;
        //获取句子
        Map map = sentenceService.getByUserId(u.getId());

        //返回JSON格式数据
        DataMap<Object> dataMap = DataMap.success().setData(map);
        return JsonResult.build(dataMap).toJSON();

    }
}

