package com.test.inside.service.Impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.CalendarMapper;

import com.test.inside.model.entity.DataMap;
import com.test.inside.model.pojo.Calendar;

import com.github.binarywang.java.emoji.EmojiConverter;

import com.test.inside.service.CalendarService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;


import java.sql.Timestamp;
import java.util.List;


@Service
public class CalendarServiceImpl extends ServiceImpl<CalendarMapper, Calendar> implements CalendarService {

    @Autowired
    CalendarMapper calendarMapper;

    private static EmojiConverter emojiConverter = EmojiConverter.getInstance();


    public DataMap sortDateTime(Integer userid){
        List<Calendar> calendars = calendarMapper.getPlanDate(userid);
        if(calendars.size() == 0){
            return null;
        }
        for(int i=0; i< calendars.size();++i){
            calendars.get(i).setEmoji(emojiConverter.toUnicode(calendars.get(i).getEmoji()));
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(calendars);
        return DataMap.success().setData(jsonArray);
    }

    public boolean addCanlendar(Integer userid, String listCalendar){
        JSONObject jsonObject = JSONObject.parseObject(listCalendar);
        Calendar calendar = new Calendar();
        if(jsonObject.containsKey("id")){
            calendar.setUserid(userid);
            calendar.setDate(Timestamp.valueOf(jsonObject.getString("date")) );
            calendar.setContent(jsonObject.getString("content"));
            calendar.setEmoji(emojiConverter.toAlias(jsonObject.getString("emoji")));
            calendar.setId(Integer.parseInt(jsonObject.getString("id")));
            calendarMapper.updateById(calendar);
            return true;
        }else{
            calendar.setUserid(userid);
            calendar.setDate(Timestamp.valueOf(jsonObject.getString("date")) );
            calendar.setContent(jsonObject.getString("content"));
            calendar.setEmoji(emojiConverter.toAlias(jsonObject.getString("emoji")));
        }
        if(calendarMapper.selectAll()==null){
            calendar.setId(1);
        }else{
            calendar.setId(calendarMapper.selectAll()+1);
        }
        calendarMapper.insert(calendar);
        return true;
    }

    public boolean deleteCanlendar(Integer id){
        if(id == null){
            return false;
        }
        calendarMapper.deleteById(id);
        return true;
    }

}
