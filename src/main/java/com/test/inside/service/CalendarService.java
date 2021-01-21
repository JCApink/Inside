package com.test.inside.service;

import com.test.inside.model.entity.DataMap;
import com.test.inside.model.pojo.Calendar;
import com.baomidou.mybatisplus.extension.service.IService;


public interface CalendarService extends IService<Calendar> {
    public boolean addCanlendar(Integer userid, String listCalendar);

    public DataMap sortDateTime(Integer userid);

    public boolean deleteCanlendar(Integer id);
}
