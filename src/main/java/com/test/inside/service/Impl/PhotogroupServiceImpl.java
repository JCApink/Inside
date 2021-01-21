package com.test.inside.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.GroupMapper;
import com.test.inside.mapper.PhotogroupMapper;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.model.pojo.Group;
import com.test.inside.model.pojo.Photogroup;
import com.test.inside.model.pojo.User;
import com.test.inside.service.GroupService;
import com.test.inside.service.PhotogroupService;
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
public class PhotogroupServiceImpl extends ServiceImpl<PhotogroupMapper, Photogroup> implements PhotogroupService {


    @Autowired
    PhotogroupMapper photogroupMapper;

    @Autowired
    PhotogroupService photogroupService;

    @Override
    public Boolean Initialize(Integer userid) {
        String[] listtitles = photogroupMapper.getOwnPhototitle(userid);
        if(listtitles.length<1){
            return false;
        }
        return true;
    }

    @Override
    public void createNewPhotoGroup(Integer userid) {
        for (int i = 1; i <= 4; i++) {
            photogroupService.save(new Photogroup().setListid(i).setListtitle("list"+i).setUserId(userid));
        }
    }

    @Override
    public Map getPhotogroup(Integer userid) {
        Photogroup[] photogroups = photogroupMapper.getByUserid(userid);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("photogroups",photogroups);
        return map;
    }

}

