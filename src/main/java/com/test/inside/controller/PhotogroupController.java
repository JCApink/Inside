package com.test.inside.controller;


import com.alibaba.fastjson.JSONObject;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.Group;
import com.test.inside.model.pojo.Photogroup;
import com.test.inside.model.pojo.User;
import com.test.inside.service.GroupService;
import com.test.inside.service.PhotogroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Bangtidy
 * @since 2020-07-31
 */

@Controller
@CrossOrigin
@ResponseBody
public class PhotogroupController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PhotogroupService photogroupService;
    /**
     * 修改分组
     */
    @PostMapping(value = "/setPhotogroup",produces = "application/json;charset=UTF-8")
    public String setPhotogroup(@RequestBody Photogroup photogroup, HttpServletRequest request) {
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();

           //判断是否修改了listid
            Photogroup photogroup1 = photogroupService.getById(photogroup.getId());
            if(photogroup1!=null)
            if(photogroup1.getListid()!=photogroup.getListid()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(CodeType.PHOTOGROUP_NOT_CHANGE.getMessage(),CodeType.PHOTOGROUP_NOT_CHANGE.getCode());
                return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
            }
            photogroup.setUserId(userid);
            boolean flag = photogroupService.updateById(photogroup);
            if(flag == false){
                return JsonResult.fail().toJSON();
            }
            return JsonResult.success().toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }

    @GetMapping(value = "/getPhotogroup")
    public String getPhotogroup(HttpServletRequest request) {
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if(user != null){
            User u = (User) user;
            Integer userid = u.getId();

            Map map=photogroupService.getPhotogroup(userid);

            if(map == null){
                return JsonResult.fail().toJSON();
            }
            //返回JSON格式数据
            DataMap<Object> dataMap = DataMap.success().setData(map);
            return JsonResult.build(dataMap).toJSON();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(),CodeType.USER_NOT_LOGIN.getCode());
        return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
    }

}

