package com.test.inside.controller;


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.test.inside.model.entity.JsonResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-10
 */
@RestController
@CrossOrigin
public class UserController {
    @Resource
    RedisTemplate<String,Object> redisTemplate;
    @GetMapping("/view/test")
    public JsonResult.JsonData getUserOfLogin(HttpServletRequest request) throws UnsupportedEncodingException{
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            return JsonResult.success("data",user);
        }
        return JsonResult.fail("msg","获取用户失败");
    }
}

