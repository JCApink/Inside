package com.test.inside.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.GroupMapper;

import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.Group;

import com.test.inside.model.pojo.User;
import com.test.inside.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-10
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    GroupService groupService;

    @Override
    public Group addGroup(String value, HttpServletRequest request) {
        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;

        Group group = new Group().setTitle(value).setUserId(u.getId());
        groupService.save(group);
        return group;
    }

    @Override
    public String deleteGroup(int id, HttpServletRequest request) {
        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;

        Group group = groupService.getById(id);
        if(group==null){//组别不存在
            System.out.println("该id不存在");
            return JsonResult.fail().toJSON();
        }else if(group.getTitle().equals("默认")||group.getTitle().equals("星标")){
            System.out.println("默认和星标分组不可删除");
            return JsonResult.fail().toJSON();
        }
        else {
            if(group.getUserId()==u.getId()){//是该用户的组别，即可删除
                System.out.println("删除");
                groupService.removeById(id);
            }else {//不是该用户所创建的分组
                System.out.println("不是该用户的组别");
                return JsonResult.fail().toJSON();
            }
            return JsonResult.success().toJSON();
        }
    }

    @Override
    public Map getGroup(HttpServletRequest request) {
        Boolean Default = false;//是否存在默认分组
        Boolean Star = false;//是否存在星标分组

        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;

        Group[] group= groupMapper.getByUserId(u.getId());
        //判断是否存在默认和星标分组
        if(group!=null)
        for (int i = 0; i < group.length; i++) {
            if(group[i].getTitle().equals("默认"))
                Default = true;
            else if(group[i].getTitle().equals("星标"))
                Star = true;
        }
        if(!Default) groupService.addGroup("默认",request);
        if(!Star) groupService.addGroup("星标",request);

        group= groupMapper.getByUserId(u.getId());
        Map<String,Group[]> map = new HashMap<String,Group[]>();
        map.put("groupList",group);
        return map;
        }
}

