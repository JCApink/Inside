package com.test.inside.controller;


import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.Group;
import com.test.inside.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GroupController {

    @Autowired
    GroupService groupService;

    /**
     * 添加分组
     */
    @PostMapping("/addGroup")
    public String addGroup(@RequestParam String value,HttpServletRequest request) {
        Group group = groupService.addGroup(value, request);
        System.out.println(group);
        return JsonResult.success().toJSON();
    }

    /**
     * 删除分组
     */
    @PostMapping("/deleteGroup")
    public  String deleteGroup(@RequestParam int id,HttpServletRequest request){
         return  groupService.deleteGroup(id,request);
    }

    /**
     * 获取分组
     */
    @GetMapping(value = "/getGroup",produces = "application/json;charset=UTF-8")
    public  String getGroup(HttpServletRequest request){
        Map map = groupService.getGroup(request);

        //返回JSON格式数据
        DataMap<Object> dataMap = DataMap.success().setData(map);
        return JsonResult.build(dataMap).toJSON();
    }
}

