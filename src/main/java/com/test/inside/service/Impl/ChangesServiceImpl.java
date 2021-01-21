package com.test.inside.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.ChangesMapper;
import com.test.inside.mapper.SentenceMapper;
import com.test.inside.model.pojo.Changes;
import com.test.inside.model.pojo.Changes_array;
import com.test.inside.model.pojo.Sentence;
import com.test.inside.model.pojo.User;
import com.test.inside.service.ChangesService;
import com.test.inside.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-25
 */
@Service
public class ChangesServiceImpl extends ServiceImpl<ChangesMapper, Changes> implements ChangesService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ChangesMapper changesMapper;

    @Autowired
    ChangesService changesService;

    @Override
    public Boolean subChanges(Integer userid, List<Changes_array> changes_arrays) {
        changesMapper.removeById(userid);
        //System.out.println(changes_arrays.size());
        for (int i = 0; i < changes_arrays.size(); i++) {
            //System.out.println(changes_arrays.get(i).toString());
            for (int j = 0; j < changes_arrays.get(i).getTags().length; j++) {
                Changes changes = new Changes()
                        .setUserid(userid)
                        .setTitle(changes_arrays.get(i).getTitle())
                        .setTag(changes_arrays.get(i).getTags()[j]);
                changesService.save(changes);
            }
        }
        return true;
    }

    @Override
    public Map getChanges(Integer userid) {
        Changes_array changes_arrays1 =new Changes_array();
        Changes_array changes_arrays2 =new Changes_array();
        List<String> str1 = new ArrayList<String>();
        List<String> str2 = new ArrayList<String>();
        int flag = 0;

        Changes[] changes = changesMapper.getByUserid(userid);//从数据库获取数据
        if(changes==null){
            System.out.println("为空");
            return null;
        }
        //转化格式
        changes_arrays1.setTitle(changes[0].getTitle());
        for (int i = 0; i < changes.length; i++) {
           if(changes[i].getTitle().equals(changes_arrays1.getTitle())){
               //System.out.println(changes[i].getTag());
               str1.add(changes[i].getTag());
           }else {
               if(flag==0){
                   changes_arrays2.setTitle(changes[i].getTitle());
                   flag = 1;
               }
               //System.out.println(changes[i].getTag());
               str2.add(changes[i].getTag());
           }
        }
        changes_arrays1.setTags(str1.toArray(new String[]{}));
        changes_arrays2.setTags(str2.toArray(new String[]{}));

        //数据格式化
        Map<String,Changes_array> map = new HashMap<String,Changes_array>();
        map.put("list1",changes_arrays1);
        map.put("list2",changes_arrays2);
        return map;
    }
}
