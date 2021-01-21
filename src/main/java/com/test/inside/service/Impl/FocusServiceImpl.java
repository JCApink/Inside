package com.test.inside.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.FocusMapper;
import com.test.inside.mapper.WisdomMapper;
import com.test.inside.model.pojo.Focus;
import com.test.inside.model.pojo.Wisdom;
import com.test.inside.service.FocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@Service
public class FocusServiceImpl extends ServiceImpl<FocusMapper, Focus> implements FocusService {

    @Autowired
    FocusService focusService;

    @Autowired
    FocusMapper focusMapper;

    @Autowired
    WisdomMapper wisdomMapper;

    @Override
    public Focus getFocusTime(Integer userid) {
        Focus focus = focusMapper.getById(userid);
        //若数据库还没有数据
        if (focus == null) {
            focus = new Focus();
            focus.setId(userid);
            focus.setTodayfocustime(0);
            focus.setTotalfocustime(0);
           java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            System.out.println(date);
            focus.setUptime(date.toString());
            focusService.save(focus);
        }
        Wisdom wisdom = wisdomMapper.getwisdom();
        //System.out.println(wisdom.toString());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("content",wisdom.getContent());
        map.put("source",wisdom.getSource());
        focus.setSentence(map);
        return focus;
    }
    @Override
    public Boolean addFocusTime(Integer userid, int time) {

        Focus focus = focusMapper.getById(userid);
        if(focus==null){
            return false;
        }
        //若更新时间为空或者不是今天，即为今天专注时间为0。
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        if(focus.getUptime()==null||!focus.getUptime().equals(date.toString())){
            focus.setTodayfocustime(time);
            focus.setTotalfocustime(focus.getTotalfocustime()+time);
            focus.setUptime(date.toString());
        }else{//更新时间与今天相同，即在原来的数字上相加
            focus.setTodayfocustime(focus.getTodayfocustime()+time);
            focus.setTotalfocustime(focus.getTotalfocustime()+time);
        }
        focusService.saveOrUpdate(focus);
        return true;
    }
}
