package com.test.inside.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.Record_solrMapper;
import com.test.inside.mapper.RecordsMapper;
import com.test.inside.model.document.Record_solr;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.*;
import com.test.inside.service.GroupService;
import com.test.inside.service.RecordsService;
import com.test.inside.util.Photo_sort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-10
 */
@Service
public class RecordsServiceImpl extends ServiceImpl<RecordsMapper, Records> implements RecordsService{

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RecordsService recordsService;

    @Autowired
    Record_solrMapper record_solrMapper;

    @Autowired
    RecordsMapper recordsMapper;

    @Autowired
    GroupService groupService;


    @Override
    public Records addRecord(Records records, HttpServletRequest request) {
        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;

        Records temp = null;
        if(records.getId()!=null)
        temp = recordsMapper.getOneById(records.getId(),u.getId());

        if(temp == null){
            //若该文章不存在，新建一个
            temp = new Records();
        }

        BeanUtils.copyProperties(records,temp,"group");
        temp.setUpdateTime(LocalDateTime.now());
        temp.setUser_id(u.getId());
        recordsService.saveOrUpdate(temp);
        return temp;
    }

    @Override
    public Map getByGroud(Group group, HttpServletRequest request) throws IOException {
        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;

        Record_solr[] records = null;

        //group为星标时
        if(group.getTitle().equals("星标")){
           records = record_solrMapper.getByStar(u.getId());
        }else{
            records = record_solrMapper.getByGroup(group.getName(),u.getId());
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name",group.getTitle());
        map.put("size",records.length);
        map.put("records",records);
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("imageCollection",map);
        return map;
    }

    @Override
    public String deleteRecord(int id, HttpServletRequest request) {
        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;

        Records record = recordsMapper.getOneById(id,u.getId());
        if(record==null){
            System.out.println("该文章不存在");
            return JsonResult.fail().toJSON();
        }else {
            boolean flag = recordsService.removeById(id);
            return JsonResult.success().toJSON();
        }
    }

    @Override
    public Map getNewRecords(HttpServletRequest request) {
        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;

        List<Record_solr> record_solrList = record_solrMapper.getNew(u.getId());
        Photo_sort.sortByTime(record_solrList);//按时间排序
        if(record_solrList.size()>5){//获取最新的五篇文章
            record_solrList = record_solrList.subList(0, 5);
        }
        //包装数据
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("records",record_solrList);
        return map;
    }

    @Override
    public Map<String, Object> getListByKey(String key,HttpServletRequest request) throws IOException{

        Record_solr[] record_solrs = record_solrMapper.selectByKey("%"+key+"%");

        Map<String,Object> map = new HashMap<>();
        map.put("list",record_solrs);
        map.put("numFound",record_solrs.length);

        return map;
    }
}
