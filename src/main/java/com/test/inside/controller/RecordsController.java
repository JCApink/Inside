package com.test.inside.controller;


import com.alibaba.fastjson.JSONObject;

import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;

import com.test.inside.model.pojo.Group;
import com.test.inside.model.pojo.Records;

import com.test.inside.service.RecordsService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-02
 */
@RestController
@CrossOrigin
public class RecordsController {

    @Autowired
    RecordsService recordsService;


    /**
     * 添加文章
     */
    @PostMapping(value = "/addRecord",produces = "application/json;charset=UTF-8")
    public String addRecord(@RequestBody Records records, HttpServletRequest request) throws IOException, SolrServerException {
        if(records.getGroupId()==null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CodeType.GroupId_BLANK.getMessage(),CodeType.GroupId_BLANK.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        }
        Records record = recordsService.addRecord(records, request);
        if(record==null){
            return JsonResult.fail().toJSON();
        }
        return JsonResult.success().toJSON();
    }

    /**
     * 请求文章组
     */
    @PostMapping(value = "/getRecords",produces = "application/json;charset=UTF-8")
    public String getRecords(@RequestBody Group group, HttpServletRequest request) throws IOException, SolrServerException {
        Map map=recordsService.getByGroud(group,request);

        //返回JSON格式数据
        DataMap<Object> dataMap = DataMap.success().setData(map);
        return JsonResult.build(dataMap).toJSON();
    }

    /**
     * 删除文章
     */
    @PostMapping("/deleteRecord")
    public String deleteRecord(@RequestParam int id, HttpServletRequest request) {
        String result = recordsService.deleteRecord(id, request);

        return result;
    }

    /**
     * 请求最新的文章组
     */
    @GetMapping(value = "/getNewRecords",produces = "application/json;charset=UTF-8")
    public String getNewRecords(HttpServletRequest request) {
        Map map=recordsService.getNewRecords(request);

        //返回JSON格式数据
        DataMap<Object> dataMap = DataMap.success().setData(map);
        return JsonResult.build(dataMap).toJSON();
    }

    /**
     * 通过关键字查询博客
     * @param key
     * @return
     */
    @GetMapping(value = "/getRecordsBykey",produces = "application/json;charset=UTF-8")
    public  Object getRecordsBykey(String key,HttpServletRequest request) throws IOException, SolrServerException {
        Map<String,Object> map =recordsService.getListByKey(key,request);
        return map;
    }
}